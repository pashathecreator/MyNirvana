package com.skelrath.mynirvana.presentation.activities.meditations.meditationCoursesActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skelrath.mynirvana.databinding.ActivityMeditationCourseBinding
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.skelrath.mynirvana.presentation.activities.meditations.meditationTimerActivity.MeditationTimerActivity
import com.skelrath.mynirvana.presentation.dialogs.meditation.meditationCourseCompletedDialog.MeditationCourseCompletedFragment
import com.skelrath.mynirvana.presentation.dialogs.meditation.resetProgressDialog.ResetProgressFragment
import com.skelrath.mynirvana.presentation.dialogs.meditation.startMeditationDialog.StartMeditationFragment
import com.skelrath.mynirvana.presentation.recycler.RecyclerViewType
import com.skelrath.mynirvana.presentation.recycler.adapters.meditation.BigMeditationRecyclerAdapter
import com.skelrath.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationOnClickListener
import com.skelrath.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MeditationCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditationCourseBinding
    private val viewModel: MeditationCourseViewModel by viewModels()

    private lateinit var meditationsAdapter: BigMeditationRecyclerAdapter
    private lateinit var meditationCoursesData: List<Meditation>

    private var meditationToStart: Meditation? = null
    private lateinit var providedMeditationCourse: MeditationCourse

    private var launcherForMeditationTimerActivity: ActivityResultLauncher<Intent>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditationCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        deserializeDataFromIntent()
        initMeditationCoursesRecycler()
        initButtonsOnClickListeners()
        initMeditationTimerActivityLauncher()
    }

    override fun onResume() {
        super.onResume()
        if (checkIsMeditationCourseCompleted())
            openMeditationCourseCompletedFragment()
    }

    private fun initButtonsOnClickListeners() {
        with(binding) {
            backToMeditationFragmentButton.setOnClickListener {
                finish()
            }

            resetProgressButton.setOnClickListener {
                openSureToResetDialog()
            }
        }
    }

    private fun resetProgressOfCourse() {
        providedMeditationCourse.id?.let {
            viewModel.resetMeditationListInMeditationCourse(
                providedMeditationCourse.meditationList,
                it
            )
            finish()
        }
    }

    private fun openStartMeditationDialog() {
        StartMeditationFragment().also {
            it.provideLambdaCallback { userChoice: Boolean ->
                if (userChoice)
                    meditationToStart?.let { meditation -> startMeditationTimerActivity(meditation) }
            }

            meditationToStart?.name?.let { name -> it.provideMeditationName(name) }

            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun openMeditationCourseCompletedFragment() {
        MeditationCourseCompletedFragment().also {
            it.provideLambdaCallback {
                resetProgressOfCourse()
                finish()
            }
            it.providesCurrentMeditationCourse(providedMeditationCourse)
            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun openSureToResetDialog() {
        ResetProgressFragment().also {
            it.provideLambdaCallback { userChoice: Boolean ->
                if (userChoice)
                    resetProgressOfCourse()
            }

            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun deserializeDataFromIntent() {
        providedMeditationCourse =
            intent.getSerializableExtra("MEDITATION_COURSE") as MeditationCourse

        binding.meditationName.text = providedMeditationCourse.name
    }


    private fun initMeditationCoursesRecycler() {
        meditationCoursesData = providedMeditationCourse.meditationList
        meditationsAdapter = BigMeditationRecyclerAdapter(meditationCoursesData, object :
            MeditationOnClickListener {
            override fun onMeditationStart(meditation: Meditation) {
                meditationToStart = meditation
                openStartMeditationDialog()
            }

            override fun onMeditationDelete(meditation: Meditation) {}


        })

        binding.meditationsRecycler.apply {
            adapter = meditationsAdapter
            layoutManager = LinearLayoutManager(
                this@MeditationCourseActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(SideSpacingItemDecoration(32, RecyclerViewType.Vertical))
        }

    }

    private fun initMeditationTimerActivityLauncher() {
        launcherForMeditationTimerActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.let {
                        val isMeditationCompleted =
                            it.getBooleanExtra("IS_MEDITATION_COMPLETED", false)

                        val isNeedToExitToMainFragment = it.getBooleanExtra(
                            "RETURN_TO_MAIN_FRAGMENT", false
                        )

                        if (isMeditationCompleted) {
                            meditationCompleted()
                            meditationsAdapter.notifyItemChanged(
                                meditationCoursesData.indexOf(
                                    meditationToStart
                                )
                            )
                        }
                        if (isNeedToExitToMainFragment)
                            finish()
                    }
                }
            }
    }

    private fun startMeditationTimerActivity(meditation: Meditation) {
        val intent = Intent(this, MeditationTimerActivity::class.java)
        intent.putExtra("MEDITATION_INFO", meditation)
        launcherForMeditationTimerActivity?.launch(intent)
    }

    private fun meditationCompleted() {
        val newMeditationList = mutableListOf<Meditation>()
        for (meditation in providedMeditationCourse.meditationList) {
            if (meditation == meditationToStart) {
                meditation.isMeditationCompleted = true
            }
            newMeditationList.add(meditation)
        }
        providedMeditationCourse.id?.let {
            viewModel.insertMeditationListInMeditationCourse(
                newMeditationList,
                it
            )
        }
    }

    private fun checkIsMeditationCourseCompleted(): Boolean {
        var countCompletedMeditations = 0
        for (meditation in providedMeditationCourse.meditationList) {
            if (meditation.isMeditationCompleted)
                countCompletedMeditations += 1
        }

        return countCompletedMeditations == providedMeditationCourse.meditationList.size
    }
}