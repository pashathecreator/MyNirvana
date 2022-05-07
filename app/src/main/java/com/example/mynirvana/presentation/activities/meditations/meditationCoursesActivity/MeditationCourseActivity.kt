package com.example.mynirvana.presentation.activities.meditations.meditationCoursesActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.ActivityMeditationCourseBinding
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.presentation.activities.meditations.meditationTimerActivity.MeditationTimerActivity
import com.example.mynirvana.presentation.dialogs.meditationCourseCompletedDialog.MeditationCourseCompletedFragment
import com.example.mynirvana.presentation.dialogs.resetProgressDialog.ResetProgressFragment
import com.example.mynirvana.presentation.dialogs.startMeditationDialog.StartMeditationFragment
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import com.example.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationOnClickListener
import com.example.mynirvana.presentation.recycler.adapters.meditation.BigMeditationRecyclerAdapter
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MeditationCourseActivity : AppCompatActivity(),
    UserChoiceAboutMeditationFragmentDialogCallback, MeditationCourseActivityCallback,
    ResetProgressCallback, MeditationCourseCompletedFragmentOnDismissCallback {

    private lateinit var binding: ActivityMeditationCourseBinding
    private val viewModel: MeditationCourseViewModel by viewModels()

    private lateinit var meditationsAdapter: BigMeditationRecyclerAdapter
    private lateinit var meditationCoursesData: List<Meditation>

    private var isMeditationNeedToBeStarted: Boolean = false
    private var pickedMeditation: Meditation? = null
    private lateinit var providedMeditationCourse: MeditationCourse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditationCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        deserializeDataFromIntent()
        initMeditationCoursesRecycler()
        initButtonsOnClickListeners()

    }

    override fun onResume() {
        super.onResume()
        if (checkIsMeditationCourseCompleted())
            openMeditationCourseCompletedFragment()
    }

    private fun initButtonsOnClickListeners() {
        with(binding) {
            backToMeditationFragmentButton.setOnClickListener {
                onBackPressed()
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
            onBackPressed()
        }
    }

    private fun openSureToResetDialog() {
        ResetProgressFragment().also {
            it.providesCallback(this)
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
                val dialog = StartMeditationFragment()
                dialog.provideCallback(this@MeditationCourseActivity)
                dialog.provideMeditationName(meditation.header)

                pickedMeditation = meditation

                dialog.show(supportFragmentManager, dialog.tag)
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

    private fun startMeditation(meditation: Meditation) {
        val meditationTimerActivity = MeditationTimerActivity().also {
            it.provideCallbackForMeditationCourse(this)
        }
        val intent = Intent(this, meditationTimerActivity::class.java)
        intent.putExtra("MEDITATION_INFO", meditation)
        startActivity(intent)
    }

    private fun meditationCompleted() {
        val newMeditationList = mutableListOf<Meditation>()
        for (meditation in providedMeditationCourse.meditationList) {
            if (meditation == pickedMeditation) {
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

    private fun openMeditationCourseCompletedFragment() {
        MeditationCourseCompletedFragment().also {
            it.providesCurrentMeditationCourse(providedMeditationCourse)
            it.providesCallback(this)
            it.show(supportFragmentManager, it.tag)
        }
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

    override fun meditationOnFinish(
        isMeditationCompleted: Boolean,
        isNecessaryToReturnToMeditationFragment: Boolean
    ) {
        if (isMeditationCompleted) {
            meditationCompleted()
        }
        meditationsAdapter.notifyItemChanged(meditationCoursesData.indexOf(pickedMeditation))

        if (isNecessaryToReturnToMeditationFragment) {
            onBackPressed()
        }
    }


    override fun resetProgress(userChoice: Boolean) {
        if (userChoice) {
            resetProgressOfCourse()
        }
    }


    override fun onDismissMeditationCourseCompletedFragment() {
        resetProgressOfCourse()
        onBackPressed()
    }
}