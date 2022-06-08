package com.example.mynirvana.presentation.mainFragments.meditationFragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.FragmentMeditationBinding
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.presentation.activities.meditations.meditationCoursesActivity.MeditationCourseActivity
import com.example.mynirvana.presentation.activities.meditations.meditationCreatorActivity.MeditationCreatorActivity
import com.example.mynirvana.presentation.activities.meditations.meditationTimerActivity.MeditationTimerActivity
import com.example.mynirvana.presentation.dialogs.meditation.startMeditationDialog.StartMeditationFragment
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeleteFragment
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import com.example.mynirvana.presentation.recycler.adapters.meditation.BigMeditationRecyclerAdapter
import com.example.mynirvana.presentation.recycler.adapters.meditation.MeditationCourseRecyclerAdapter
import com.example.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationCourseOnClickListener
import com.example.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationOnClickListener
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeditationFragment : Fragment() {
    private lateinit var readyMeditationAdapter: BigMeditationRecyclerAdapter
    private lateinit var userMeditationAdapter: BigMeditationRecyclerAdapter
    private lateinit var meditationCoursesAdapter: MeditationCourseRecyclerAdapter

    private lateinit var binding: FragmentMeditationBinding
    private val viewModel: MeditationFragmentViewModel by viewModels()

    private lateinit var dataForReadyMeditations: List<Meditation>
    private lateinit var dataForUserMeditations: List<Meditation>

    private var meditationToStart: Meditation? = null

    private var launcherForMeditationTimerActivity: ActivityResultLauncher<Intent>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeditationBinding.inflate(inflater)
        initRecyclerView()
        addDataSetForRecyclers()
        initCreateMeditationButton()
        initMeditationTimerActivityLauncher()
        return binding.root
    }

    private fun initMeditationTimerActivityLauncher() {

        launcherForMeditationTimerActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    meditationToStart =
                        result.data?.getSerializableExtra("MEDITATION_TO_START") as Meditation?
                    meditationToStart?.let { startMeditationTimerActivity(it) }
                }
            }

    }

    private fun initCreateMeditationButton() {
        with(binding) {
            createMeditationButton.setOnClickListener {
                val intent = Intent(activity, MeditationCreatorActivity::class.java)
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
                    meditationToStart = meditation
                    openStartMeditationDialog()
                }


                override fun onMeditationDelete(meditation: Meditation) {}
            })

        binding.readyMeditationsRecycler.adapter = readyMeditationAdapter
    }

    private fun openStartMeditationDialog() {
        StartMeditationFragment().also {
            it.provideLambdaCallback { userChoice ->
                if (userChoice)
                    meditationToStart?.let { meditation -> startMeditationTimerActivity(meditation) }
            }
            meditationToStart?.let { meditation -> it.provideMeditationName(meditation.name) }
            it.show(parentFragmentManager, it.tag)
        }
    }

    private fun addDataSetForUserMeditations() {
        viewModel.meditationLiveData.observe(viewLifecycleOwner) {
            dataForUserMeditations = it
            userMeditationAdapter =
                BigMeditationRecyclerAdapter(it, object : MeditationOnClickListener {
                    override fun onMeditationStart(meditation: Meditation) {
                        meditationToStart = meditation
                        openStartMeditationDialog()
                    }

                    override fun onMeditationDelete(meditation: Meditation) {
                        meditationToStart = meditation
                        userMeditationAdapter.notifyItemChanged(it.indexOf(meditation))
                        startOnMeditationDeleteDialog()
                    }
                })

            binding.userMeditationsRecycler.adapter = userMeditationAdapter

        }
    }

    private fun startMeditationTimerActivity(meditation: Meditation) {
        val intent = Intent(activity, MeditationTimerActivity::class.java)
        intent.putExtra("MEDITATION_INFO", meditation)
        launcherForMeditationTimerActivity?.launch(intent)
    }

    private fun startOnMeditationDeleteDialog() {
        UserDeleteFragment().also {
            it.provideLambdaCallback { userChoice ->
                if (userChoice) {
                    meditationToStart?.let { meditation ->
                        viewModel.deleteMeditationFromDataBase(
                            meditation
                        )
                    }
                }
                userMeditationAdapter.notifyItemChanged(
                    dataForUserMeditations.indexOf(
                        meditationToStart
                    )
                )
            }
            meditationToStart?.let { meditation -> it.provideMeditation(meditation) }
            it.show(parentFragmentManager, it.tag)
        }
    }


    private fun startMeditationCourse(meditationCourse: MeditationCourse) {
        Intent(activity, MeditationCourseActivity::class.java).also {
            it.putExtra("MEDITATION_COURSE", meditationCourse)
            startActivity(it)
        }
    }
}