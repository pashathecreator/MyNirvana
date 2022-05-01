package com.example.mynirvana.presentation.activities.meditationCoursesActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.ActivityMeditationCourseBinding
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.presentation.activities.meditationTimerActivity.MeditationTimerActivity
import com.example.mynirvana.presentation.dialogs.startMeditationDialog.StartMeditationFragmentDialog
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import com.example.mynirvana.presentation.recycler.onClickListeners.MeditationOnClickListener
import com.example.mynirvana.presentation.recycler.adapters.BigMeditationRecyclerAdapter
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MeditationCourseActivity : AppCompatActivity(),
    UserChoiceAboutMeditationFragmentDialogCallback, MeditationCourseActivityCallback {

    private lateinit var binding: ActivityMeditationCourseBinding
    private lateinit var providedMeditationCourse: MeditationCourse
    private lateinit var meditationsAdapter: BigMeditationRecyclerAdapter
    private lateinit var meditationCoursesData: List<Meditation>
    private var isMeditationNeedToBeStarted: Boolean = false
    private var pickedMeditation: Meditation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deserializeDataFromIntent()
        initMeditationCoursesRecycler()
        initButtonsOnClickListeners()

    }

    private fun initButtonsOnClickListeners() {
        with(binding) {
            backToMeditationFragmentButton.setOnClickListener {
                onBackPressed()
            }
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
                val dialog = StartMeditationFragmentDialog()
                dialog.provideCallback(this@MeditationCourseActivity)
                dialog.provideMeditationName(meditation.header)

                pickedMeditation = meditation

                dialog.show(supportFragmentManager, dialog.tag)
            }

            override fun onMeditationSureDelete(meditation: Meditation) {}


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


    override fun sendUserChoiceFromFragmentDialog(userChoice: Boolean) {
        this.isMeditationNeedToBeStarted = userChoice
    }

    override fun userChoiceFragmentDialogDismissed(isDismissedByCrossButton: Boolean) {
        if (!isDismissedByCrossButton) {
            if (isMeditationNeedToBeStarted)
                pickedMeditation?.let { startMeditation(it) }
        }
    }

    override fun meditationOnFinish(isMeditationCompleted: Boolean) {
        if (isMeditationCompleted) {
            pickedMeditation?.isMeditationCompleted = true
        }
        meditationsAdapter.notifyItemChanged(meditationCoursesData.indexOf(pickedMeditation))

    }

}