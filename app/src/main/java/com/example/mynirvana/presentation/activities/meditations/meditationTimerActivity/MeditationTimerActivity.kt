package com.example.mynirvana.presentation.activities.meditations.meditationTimerActivity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityMeditationTimerBinding
import com.example.mynirvana.domain.backgroundSounds.ReadyBackgroundSounds
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.presentation.activities.meditations.meditationCoursesActivity.MeditationCourseActivityCallback
import com.example.mynirvana.presentation.activities.timerState.TimerState
import com.example.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment.BackGroundSoundChoiceFragmentForMeditationTimer
import com.example.mynirvana.presentation.dialogs.meditation.exitFromMeditationDialog.ExitFromMeditationFragment
import com.example.mynirvana.presentation.dialogs.meditation.exitFromMeditationDialog.ExitFromMeditationToMeditationCoursesFragment
import com.example.mynirvana.presentation.dialogs.meditation.meditationOnFinishDialog.MeditationOnFinishForCourseFragment
import com.example.mynirvana.presentation.mainFragments.homeFragment.AskingForStartMeditation
import com.example.mynirvana.presentation.dialogs.meditation.meditationOnFinishDialog.MeditationOnFinishFragment
import com.example.mynirvana.presentation.dialogs.meditation.userChoiceCallback.UserChoiceAboutMeditationDialogCallback
import com.example.mynirvana.presentation.timeConvertor.TimeWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeditationTimerActivity : AppCompatActivity(), BackgroundSoundsCallback,
    UserChoiceAboutMeditationDialogCallback, MeditationOnFinishFragmentCallback {

    private lateinit var binding: ActivityMeditationTimerBinding
    private val viewModel: MeditationTimerViewModel by viewModels()

    private lateinit var providedMeditation: Meditation
    private var totalTimeInSeconds: Long = 0
    private var meditationName: String = ""
    private var backgroundMeditationSound: Int = R.raw.rain_sound
    private var endMeditationSound: Int = R.raw.guitar_sound

    private var secondsRemainingInString: String = ""
    private var currentSecondsRemaining: Long = 0
    private var currentAction: TimerState = TimerState.Playing

    private var isMeditationCanBeRestarted: Boolean = true


    companion object {
        private lateinit var callbackForFragment: AskingForStartMeditation
        private lateinit var callbackForMeditationCourseActivity: MeditationCourseActivityCallback
    }


    fun provideCallbackForFragment(callbackForFragment: AskingForStartMeditation) {
        MeditationTimerActivity.callbackForFragment = callbackForFragment
    }

    fun provideCallbackForMeditationCourse(callbackForMeditationCourseActivity: MeditationCourseActivityCallback) {
        MeditationTimerActivity.callbackForMeditationCourseActivity =
            callbackForMeditationCourseActivity
    }

    override fun onBackPressed() {
        if (isMeditationCanBeRestarted) {
            openExitFromMeditationDialog()
        } else {
            openExitFromMeditationToCoursesDialog()
        }
    }

    private fun openExitFromMeditationToCoursesDialog() {
        if (!ExitFromMeditationToMeditationCoursesFragment.isDialogResumed) {
            ExitFromMeditationToMeditationCoursesFragment().also {
                it.provideCallback(this)
                it.show(supportFragmentManager, it.tag)
                Log.d("debug", (it.isResumed).toString())
            }
        }
    }

    private fun openExitFromMeditationDialog() {
        if (!ExitFromMeditationFragment.isDialogResumed) {
            ExitFromMeditationFragment().also {
                it.provideCallback(this)
                it.show(supportFragmentManager, it.tag)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()

        providedMeditation = intent.getSerializableExtra("MEDITATION_INFO") as Meditation
        parseMeditationData(providedMeditation)

        initSoundsForViewModel()
        startTimer()
        startBackgroundSound()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.currentBackgroundSoundButton.setOnClickListener {
            val bottomSheetDialog = BackGroundSoundChoiceFragmentForMeditationTimer(
                this,
                binding.currentBackgroundSoundButton.text as String
            )

            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }



        binding.actionButton.setOnClickListener {
            currentAction =
                if (currentAction == TimerState.Paused) TimerState.Playing else TimerState.Paused

            when (currentAction) {
                TimerState.Paused -> pauseCountDownTimer()
                TimerState.Playing -> playCountDownTimer()
            }
        }

    }

    private fun initSoundsForViewModel() {
        if (backgroundMeditationSound != 0) {
            viewModel.providesBackgroundSound(backgroundMeditationSound)
        }
        viewModel.providesEndSound(endMeditationSound)
    }


    private fun pauseCountDownTimer() {
        binding.actionButton.setImageResource(R.drawable.ic_play_icon)
        viewModel.pauseTimer()
        pauseBackgroundSound()
    }

    private fun playCountDownTimer() {
        binding.actionButton.setImageResource(R.drawable.ic_pause_icon)
        viewModel.startTimer(totalTimeInSeconds)
        startBackgroundSound()
    }

    private fun updateCountDownTimerUI() {
        binding.timeTV.text = secondsRemainingInString
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.progressCountdown.setProgress(
                (currentSecondsRemaining.toDouble() / totalTimeInSeconds.toDouble() * 100).toInt(),
                true
            )
        }
    }

    private fun initObserver() {
        viewModel.remainingTime.observe(this) {
            this.secondsRemainingInString =
                TimeWorker.convertTimeFromSecondsToMinutesFormatWithoutTimeWord(it)
            currentSecondsRemaining = it
            if (currentSecondsRemaining == 0L) {
                timerOnFinish()
            }
            updateCountDownTimerUI()
        }
    }

    private fun getBackgroundSounds(): List<BackgroundSound> {
        val readyMeditations = mutableListOf<BackgroundSound>()

        ReadyBackgroundSounds.values().forEach {
            val name = it.backgroundSound.name
            val image = it.backgroundSound.icon
            val sound = it.backgroundSound.sound

            readyMeditations.add(BackgroundSound(name = name, icon = image, sound = sound))
        }

        return readyMeditations

    }

    private fun parseMeditationData(meditation: Meditation) {
        totalTimeInSeconds = meditation.time
        meditationName = meditation.name
        backgroundMeditationSound = meditation.backgroundSoundResourceId
        endMeditationSound = meditation.endSoundResourceId
        isMeditationCanBeRestarted = meditation.isMeditationCanBeRestarted
        val meditationSoundsList = getBackgroundSounds()
        var backgroundSoundName = ""
        for (backgroundSound in meditationSoundsList) {
            if (backgroundSound.sound == backgroundMeditationSound) {
                backgroundSoundName = backgroundSound.name
            }
        }
        binding.currentBackgroundSoundButton.text = backgroundSoundName
    }

    private fun startTimer() {
        viewModel.startTimer(totalTimeInSeconds)
    }


    override fun sendPickedBackgroundSound(backgroundSound: BackgroundSound) {
        backgroundMeditationSound = backgroundSound.sound
        binding.currentBackgroundSoundButton.text = backgroundSound.name
        viewModel.providesBackgroundSound(backgroundMeditationSound)

        if (currentAction == TimerState.Playing) {
            pauseBackgroundSound()
            startBackgroundSound()
        }
    }

    override fun sendUserChoiceFromMeditationStartDialog(userChoice: Boolean) {
        if (userChoice) {
            pauseBackgroundSound()
            super.onBackPressed()
        }
    }

    private var isNeedToExitToMainFragment: Boolean = false

    override fun meditationOnFinishUserChoice(userChoice: Boolean) {
        isNeedToExitToMainFragment = userChoice
    }

    private var userChoiceAboutReturnToMainFragment: Boolean = false

    override fun meditationOnFinishFragmentDestroyed() {
        if (isMeditationCanBeRestarted) {
            if (isNeedToExitToMainFragment) {
                stopEndSound()
                super.onBackPressed()
            } else {
                callbackForFragment.asksForStartMeditation(
                    providedMeditation
                )
                stopEndSound()
                super.onBackPressed()
            }
        } else {
            if (isNeedToExitToMainFragment) {
                stopEndSound()
                userChoiceAboutReturnToMainFragment = true
                super.onBackPressed()
            } else {
                stopEndSound()
                super.onBackPressed()
            }
        }


    }

    private fun startBackgroundSound() {
        if (backgroundMeditationSound != 0) {
            viewModel.startBackgroundSound(backgroundMeditationSound)
        }
    }

    private fun pauseBackgroundSound() {
        viewModel.pauseBackgroundSound()
    }

    private fun stopEndSound() {
        viewModel.stopMeditationMediaPlayer()
    }

    private var isMeditationCompleted = false

    private fun timerOnFinish() {
        isMeditationCompleted = true
        if (isMeditationCanBeRestarted) {
            if (!MeditationOnFinishFragment.isDialogResumed) {
                MeditationOnFinishFragment().also {
                    it.provideCallback(this)
                    it.provideTimeForMeditation(providedMeditation.time)
                    it.isCancelable = false
                    it.show(supportFragmentManager, it.tag)
                }
            }
        } else {
            if (!MeditationOnFinishForCourseFragment.isDialogResumed) {
                MeditationOnFinishForCourseFragment().also {
                    it.provideCallback(this)
                    it.isCancelable = false
                    it.show(supportFragmentManager, it.tag)
                }
            }
        }

    }

    override fun onDestroy() {
        when (isMeditationCanBeRestarted) {
            true -> callbackForFragment.onReadyToStartMeditation()
            false -> callbackForMeditationCourseActivity.meditationOnFinish(
                isMeditationCompleted,
                userChoiceAboutReturnToMainFragment
            )
        }
        stopEndSound()
        super.onDestroy()
    }

}


