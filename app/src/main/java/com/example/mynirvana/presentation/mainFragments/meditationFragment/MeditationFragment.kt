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
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.presentation.activities.meditations.meditationCoursesActivity.MeditationCourseActivity
import com.example.mynirvana.presentation.activities.meditations.meditationCreatorActivity.MeditationCreatorActivity
import com.example.mynirvana.presentation.activities.meditations.meditationTimerActivity.MeditationTimerActivity
import com.example.mynirvana.presentation.dialogs.meditation.startMeditationDialog.StartMeditationFragment
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeleteMeditationCallback
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeleteFragment
import com.example.mynirvana.presentation.dialogs.meditation.userChoiceCallback.UserChoiceAboutMeditationDialogCallback
import com.example.mynirvana.presentation.mainFragments.homeFragment.AskingForStartMeditation
import com.example.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationOnClickListener
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import com.example.mynirvana.presentation.recycler.adapters.meditation.BigMeditationRecyclerAdapter
import com.example.mynirvana.presentation.recycler.adapters.meditation.MeditationCourseRecyclerAdapter
import com.example.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationCourseOnClickListener
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeditationFragment : Fragment(), UserChoiceAboutMeditationDialogCallback,
    AskingForStartMeditation, UserDeleteMeditationCallback {
    private lateinit var readyMeditationAdapter: BigMeditationRecyclerAdapter
    private lateinit var userMeditationAdapter: BigMeditationRecyclerAdapter
    private lateinit var meditationCoursesAdapter: MeditationCourseRecyclerAdapter

    private lateinit var binding: FragmentMeditationBinding
    private val viewModel: MeditationFragmentViewModel by viewModels()

    private lateinit var dataForReadyMeditations: List<Meditation>
    private lateinit var dataForUserMeditations: List<Meditation>

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
                MeditationCreatorActivity().also {
                    it.provideCallback(
                        this@MeditationFragment
                    )
                    val intent = Intent(activity, it::class.java)
                    startActivity(intent)
                }
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
        viewModel.meditationCourseLiveData.observe(viewLifecycleOwner) {
            it?.let {
                meditationCoursesAdapter = MeditationCourseRecyclerAdapter(
                    it, object : MeditationCourseOnClickListener {
                        override fun onMeditationCourseStart(meditationCourse: MeditationCourse) {
                            startMeditationCourse(meditationCourse)
                        }
                    }
                )
                binding.meditationCoursesRecycler.adapter = meditationCoursesAdapter
            }
        }

    }

    private fun addDataSetForReadyMeditations() {
        dataForReadyMeditations = viewModel.getReadyMeditations()
        readyMeditationAdapter = BigMeditationRecyclerAdapter(
            dataForReadyMeditations,
            object : MeditationOnClickListener {
                override fun onMeditationStart(meditation: Meditation) {
                    val dialog = StartMeditationFragment()
                    dialog.provideCallback(this@MeditationFragment)
                    dialog.provideMeditationName(meditation.name)

                    pickedMeditation = meditation

                    dialog.show(parentFragmentManager, dialog.tag)
                }


                override fun onMeditationDelete(meditation: Meditation) {
                }


            })

        binding.readyMeditationsRecycler.adapter = readyMeditationAdapter
    }

    private fun addDataSetForUserMeditations() {
        viewModel.meditationLiveData.observe(viewLifecycleOwner) {
            dataForUserMeditations = it
            userMeditationAdapter =
                BigMeditationRecyclerAdapter(it, object : MeditationOnClickListener {
                    override fun onMeditationStart(meditation: Meditation) {
                        val dialog = StartMeditationFragment()
                        dialog.provideCallback(this@MeditationFragment)
                        dialog.provideMeditationName(meditation.name)
                        pickedMeditation = meditation
                        dialog.show(parentFragmentManager, dialog.tag)
                    }

                    override fun onMeditationDelete(meditation: Meditation) {
                        pickedMeditation = meditation
                        userMeditationAdapter.notifyItemChanged(it.indexOf(meditation))
                        startOnMeditationDeleteDialog()
                    }
                })

            binding.userMeditationsRecycler.adapter = userMeditationAdapter

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
        UserDeleteFragment().also {
            it.provideCallbackForMeditation(this)
            pickedMeditation?.let { meditation -> it.provideMeditation(meditation) }
            it.show(parentFragmentManager, it.tag)
        }
    }


    private fun startMeditationCourse(meditationCourse: MeditationCourse) {
        val meditationCourseActivity = MeditationCourseActivity()
        val intent = Intent(activity, meditationCourseActivity::class.java)
        intent.putExtra("MEDITATION_COURSE", meditationCourse)
        startActivity(intent)
    }


    override fun sendUserChoiceFromMeditationStartDialog(userChoice: Boolean) {
        if (userChoice)
            pickedMeditation?.let { startMeditation(it) }
    }

    private var meditationThatNeedToBeStarted: Meditation? = null

    override fun asksForStartMeditation(meditation: Meditation) {
        meditationThatNeedToBeStarted = meditation
    }

    override fun onReadyToStartMeditation() {
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