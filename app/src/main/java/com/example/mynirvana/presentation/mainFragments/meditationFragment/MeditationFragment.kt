package com.example.mynirvana.presentation.mainFragments.meditationFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.FragmentMeditationBinding
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.domain.meditations.model.MeditationCourse
import com.example.mynirvana.presentation.activities.meditationCoursesActivity.MeditationCourseActivity
import com.example.mynirvana.presentation.activities.meditationCreatorActivity.MeditationCreatorActivity
import com.example.mynirvana.presentation.activities.meditationTimerActivity.MeditationTimerActivity
import com.example.mynirvana.presentation.dialogs.startMeditationDialog.StartMeditationFragmentDialog
import com.example.mynirvana.presentation.dialogs.sureToDeleteMeditation.DeleteMeditationCallback
import com.example.mynirvana.presentation.dialogs.sureToDeleteMeditation.SureToDeleteMeditationFragment
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback
import com.example.mynirvana.presentation.mainFragments.homeFragment.AskingForStartMeditation
import com.example.mynirvana.presentation.recycler.onClickListeners.MeditationOnClickListener
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import com.example.mynirvana.presentation.recycler.adapters.BigMeditationRecyclerAdapter
import com.example.mynirvana.presentation.recycler.adapters.MeditationCourseRecyclerAdapter
import com.example.mynirvana.presentation.recycler.onClickListeners.MeditationCourseOnClickListener
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeditationFragment : Fragment(), UserChoiceAboutMeditationFragmentDialogCallback,
    AskingForStartMeditation, DeleteMeditationCallback {
    private lateinit var readyMeditationAdapter: BigMeditationRecyclerAdapter
    private lateinit var userMeditationAdapter: BigMeditationRecyclerAdapter
    private lateinit var meditationCoursesAdapter: MeditationCourseRecyclerAdapter

    private lateinit var binding: FragmentMeditationBinding
    private val viewModel: MeditationFragmentViewModel by viewModels()

    private lateinit var dataForReadyMeditations: List<Meditation>
    private lateinit var dataForUserMeditations: List<Meditation>
    private lateinit var dataForCourses: List<MeditationCourse>

    private var isMeditationNeedToBeStarted: Boolean = false
    private var pickedMeditation: Meditation? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeditationBinding.inflate(inflater)
        initRecyclerView()
        addDataSetForRecyclers()
        initCreateMeditationButton()
        return binding.root
    }

    private fun initCreateMeditationButton() {
        with(binding) {
            createMeditationButton.setOnClickListener {
                val meditationCreatorActivity = MeditationCreatorActivity()
                meditationCreatorActivity.provideCallback(
                    this@MeditationFragment
                )
                val intent = Intent(activity, meditationCreatorActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initRecyclerView() {
        binding.meditationCoursesRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SideSpacingItemDecoration(32, RecyclerViewType.Vertical))
        }

        binding.readyMeditationsRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SideSpacingItemDecoration(32, RecyclerViewType.Vertical))
        }

        binding.userMeditationsRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SideSpacingItemDecoration(32, RecyclerViewType.Vertical))
        }
    }

    private fun addDataSetForRecyclers() {
        addDataSetForCourses()
        addDataSetForReadyMeditations()
        addDataSetForUserMeditations()
    }

    private fun addDataSetForCourses() {
        dataForCourses = viewModel.getMeditationCourses()
        meditationCoursesAdapter = MeditationCourseRecyclerAdapter(
            dataForCourses, object : MeditationCourseOnClickListener {
                override fun onMeditationCourseStart(meditationCourse: MeditationCourse) {
                    startMeditationCourse(meditationCourse)
                }
            }
        )
        binding.meditationCoursesRecycler.adapter = meditationCoursesAdapter
    }

    private fun addDataSetForReadyMeditations() {
        dataForReadyMeditations = viewModel.getReadyMeditations()
        readyMeditationAdapter = BigMeditationRecyclerAdapter(
            dataForReadyMeditations,
            object : MeditationOnClickListener {
                override fun onMeditationStart(meditation: Meditation) {
                    val dialog = StartMeditationFragmentDialog()
                    dialog.provideCallback(this@MeditationFragment)
                    dialog.provideMeditationName(meditation.header)

                    pickedMeditation = meditation

                    dialog.show(parentFragmentManager, dialog.tag)
                }

                override fun onMeditationSureDelete(meditation: Meditation) {}


            })

        binding.readyMeditationsRecycler.adapter = readyMeditationAdapter
    }

    private fun addDataSetForUserMeditations() {
        viewModel.meditationButtonLiveData.observe(viewLifecycleOwner) {
            dataForUserMeditations = it
            if (dataForUserMeditations.isEmpty()) {
                userHasZeroMeditations(true)
            } else {
                userHasZeroMeditations(false)

                userMeditationAdapter =
                    BigMeditationRecyclerAdapter(it, object : MeditationOnClickListener {
                        override fun onMeditationStart(meditation: Meditation) {
                            val dialog = StartMeditationFragmentDialog()
                            dialog.provideCallback(this@MeditationFragment)
                            dialog.provideMeditationName(meditation.header)
                            pickedMeditation = meditation
                            dialog.show(parentFragmentManager, dialog.tag)
                        }

                        override fun onMeditationSureDelete(meditation: Meditation) {
                            pickedMeditation = meditation
                            startOnMeditationDeleteDialog()
                        }

                    })

                binding.userMeditationsRecycler.adapter = userMeditationAdapter
            }
        }
    }

    private fun userHasZeroMeditations(isDataEmpty: Boolean) {
        if (isDataEmpty) {
            binding.userMeditationsRecycler.visibility = View.GONE
            binding.userHasZeroMeditationsTV.text =
                "Похоже, что вы еще не создали ни одной медитации"
        } else {
            binding.userMeditationsRecycler.visibility = View.VISIBLE
            binding.userHasZeroMeditationsTV.text = ""
        }
    }

    private fun startMeditation(meditation: Meditation) {
        val meditationTimerActivity =
            MeditationTimerActivity().also { it.provideCallbackForFragment(this) }
        val intent = Intent(activity, meditationTimerActivity::class.java)
        intent.putExtra("MEDITATION_INFO", meditation)
        startActivity(intent)
    }

    private fun startOnMeditationDeleteDialog() {
        val dialog = SureToDeleteMeditationFragment().also {
            it.provideCallback(this)
            pickedMeditation?.let { meditation -> it.provideMeditation(meditation) }
        }
        dialog.show(parentFragmentManager, dialog.tag)
    }


    private fun startMeditationCourse(meditationCourse: MeditationCourse) {
        val meditationCourseActivity = MeditationCourseActivity()
        val intent = Intent(activity, meditationCourseActivity::class.java)
        intent.putExtra("MEDITATION_COURSE", meditationCourse)
        startActivity(intent)
    }


    override fun sendUserChoiceFromFragmentDialog(userChoice: Boolean) {
        this.isMeditationNeedToBeStarted = userChoice
    }

    override fun userChoiceFragmentDialogDismissed(isDismissedByCrossButton: Boolean) {
        if (!isDismissedByCrossButton) {
            if (isMeditationNeedToBeStarted)
                pickedMeditation?.let { startMeditation(it) }
        }
    }

    private var meditationThatNeedToBeStarted: Meditation? = null

    override fun asksForStartMeditation(meditation: Meditation) {
        meditationThatNeedToBeStarted = meditation
    }

    override fun onMeditationActivityDestroyed() {
        meditationThatNeedToBeStarted?.let {
            startMeditation(it)
        }
        meditationThatNeedToBeStarted = null
    }

    override fun userDecidedAboutDeletingMeditation(userChoice: Boolean) {
        if (userChoice) {
            pickedMeditation?.let { viewModel.deleteMeditationFromDataBase(it) }
        }
        userMeditationAdapter.notifyItemChanged(dataForUserMeditations.indexOf(pickedMeditation))
    }


}