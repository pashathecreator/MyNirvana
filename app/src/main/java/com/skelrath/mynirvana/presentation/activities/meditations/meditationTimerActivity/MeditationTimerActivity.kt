package com.skelrath.mynirvana.presentation.activities.meditations.meditationTimerActivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.ActivityMeditationTimerBinding
import com.skelrath.mynirvana.domain.backgroundSounds.ReadyBackgroundSounds
import com.skelrath.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.presentation.activities.timerState.TimerState
import com.skelrath.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment.BackGroundSoundChoiceFragmentForMeditationTimer
import com.skelrath.mynirvana.presentation.dialogs.meditation.exitFromMeditationDialog.ExitFromMeditationFragment
import com.skelrath.mynirvana.presentation.dialogs.meditation.exitFromMeditationDialog.ExitFromMeditationToMeditationCoursesFragment
import com.skelrath.mynirvana.presentation.dialogs.meditation.meditationOnFinishDialog.MeditationOnFinishForCourseFragment
import com.skelrath.mynirvana.presentation.dialogs.meditation.meditationOnFinishDialog.MeditationOnFinishFragment
import com.skelrath.mynirvana.presentation.timeConvertor.TimeWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeditationTimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditationTimerBinding
    private val viewModel: MeditationTimerViewModel by viewModels()

    private lateinit var providedMeditation: Meditation
    private var totalTimeInSeconds: Long = 0
    private var meditationName: String = ""
    private var pickedBackgroundSound: Int = R.raw.rain_sound
    private var endMeditationSound: Int = R.raw.guitar_sound

    private var secondsRemainingInString: String = ""
    private var currentSecondsRemaining: Long = 0
    private var currentAction: TimerState = TimerState.Playing

    private var isMeditationCanBeRestarted: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationTimerBinding.inflate(layoutInflater)
        initObserver()

        providedMeditation = intent.getSerializableExtra("MEDITATION_INFO") as Meditation
        parseMeditationData(providedMeditation)

        initSoundsForViewModel()
        initButtons()

        setContentView(binding.root)

        startTimer()
        startBackgroundSound()
    }

    private fun initButtons() {
        with(binding) {
            backButton.setOnClickListener {
                onBackPressed()
            }

            currentBackgroundSoundButton.setOnClickListener {
                openBackgroundSoundPicker()
            }

            actionButton.setOnClickListener {
                currentAction =
                    if (currentAction == TimerState.Paused) TimerState.Playing else TimerState.Paused

                when (currentAction) {
                    TimerState.Paused -> pauseCountDownTimer()
                    TimerState.Playing -> playCountDownTimer()
                }
            }
        }
    }

    private fun openBackgroundSoundPicker() {
        BackGroundSoundChoiceFragmentForMeditationTimer().also {
            it.provideLambdaCallback { backgroundSound ->
                pickedBackgroundSound = backgroundSound.sound
                binding.currentBackgroundSoundButton.text = backgroundSound.name
                viewModel.providesBackgroundSound(pickedBackgroundSound)

                if (currentAction == TimerState.Playing) {
                    pauseBackgroundSound()
                    startBackgroundSound()
                }
            }
            it.provideUserChoiceName(binding.currentBackgroundSoundButton.text.toString())

            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun initSoundsForViewModel() {
        viewModel.providesBackgroundSound(pickedBackgroundSound)
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
        pickedBackgroundSound = meditation.backgroundSoundResourceId
        endMeditationSound = meditation.endSoundResourceId
        isMeditationCanBeRestarted = meditation.isMeditationCanBeRestarted
        val meditationSoundsList = getBackgroundSounds()
        var backgroundSoundName = ""
        for (backgroundSound in meditationSoundsList) {
            if (backgroundSound.sound == pickedBackgroundSound) {
                backgroundSoundName = backgroundSound.name
            }
        }
        binding.currentBackgroundSoundButton.text = backgroundSoundName
    }

    private fun startTimer() {
        viewModel.startTimer(totalTimeInSeconds)
    }

    private fun startBackgroundSound() {
        viewModel.startBackgroundSound(pickedBackgroundSound)
    }

    private fun pauseBackgroundSound() {
        viewModel.pauseBackgroundSound()
    }

    private fun stopEndSound() {
        viewModel.stopMeditationMediaPlayer()
    }

    private fun timerOnFinish() {
        if (isMeditationCanBeRestarted) {
            if (!MeditationOnFinishFragment.isDialogResumed) {
                MeditationOnFinishFragment().also {
                    it.provideLambdaCallback { userChoice: Boolean ->
                        if (!userChoice) {
                            Intent().also { intent ->
                                intent.putExtra("MEDITATION_TO_START", providedMeditation)
                                setResult(RESULT_OK, intent)
                            }
                        }
                        stopEndSound()
                        finish()
                    }
                    it.provideTimeForMeditation(providedMeditation.time)
                    it.isCancelable = false
                    it.show(supportFragmentManager, it.tag)
                }
            }
        } else {
            if (!MeditationOnFinishForCourseFragment.isDialogResumed) {
                MeditationOnFinishForCourseFragment().also {
                    it.provideLambdaCallback { userChoice: Boolean ->
                        Intent().also { intent ->
                            intent.putExtra("IS_MEDITATION_COMPLETED", true)
                            intent.putExtra("RETURN_TO_MAIN_FRAGMENT", userChoice)
                            setResult(RESULT_OK, intent)
                            stopEndSound()
                            finish()
                        }
                    }
                    it.isCancelable = false
                    it.show(supportFragmentManager, it.tag)
                }
            }
        }
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
                it.provideLambdaCallback { userChoice ->
                    if (userChoice) {
                        stopEndSound()
                        finish()
                    }
                }
                it.show(supportFragmentManager, it.tag)
            }
        }
    }

    private fun openExitFromMeditationDialog() {
        if (!ExitFromMeditationFragment.isDialogResumed) {
            ExitFromMeditationFragment().also {
                it.provideLambdaCallback { userChoice ->
                    if (userChoice) {
                        stopEndSound()
                        finish()
                    }
                }
                it.show(supportFragmentManager, it.tag)
            }
        }
    }

    override fun onDestroy() {
        pauseCountDownTimer()
        super.onDestroy()
    }
}


