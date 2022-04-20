package com.example.mynirvana.presentation.meditationTimerActivity

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityMeditationTimerBinding
import com.example.mynirvana.domain.backgroundSounds.ReadyBackgroundSounds
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.presentation.backgroundSoundChoiceFragment.BackGroundSoundChoiceFragmentForMeditationTimer
import com.example.mynirvana.presentation.dialogs.exitFromMeditationDialog.ExitFromMeditationFragment
import com.example.mynirvana.presentation.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeditationTimerActivity : AppCompatActivity(), BackgroundSoundsCallback,
    UserChoiceAboutMeditationFragmentDialogCallback {

    private var isMeditationNeedToBeEnded: Boolean = false
    private lateinit var binding: ActivityMeditationTimerBinding
    private val viewModel: MeditationTimerViewModel by viewModels()

    private var totalTimeInSeconds: Long = 0
    private var meditationName: String = ""
    private var meditationSound: Int = R.raw.rain_sound

    private var secondsRemainingInString: String = ""
    private var currentSecondsRemaining: Long = 0

    private var currentAction: TimerState = TimerState.Playing


    enum class TimerState {
        Paused, Playing
    }

    override fun onBackPressed() {
        val dialog = ExitFromMeditationFragment()
        dialog.provideCallback(this)
        dialog.show(supportFragmentManager, dialog.tag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()

        val meditation = intent.getSerializableExtra("MEDITATION_INFO") as Meditation
        parseMeditationData(meditation)

        startSound()
        startTimer()

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

    private lateinit var mediaPlayer: MediaPlayer

    private fun startSound() {
        mediaPlayer = MediaPlayer.create(this, meditationSound)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
    }

    private fun stopSound() {
        mediaPlayer.stop()
    }


    private fun pauseCountDownTimer() {
        binding.actionButton.setImageResource(R.drawable.ic_play_icon)
        viewModel.pauseTimer()
        stopSound()
    }

    private fun playCountDownTimer() {
        binding.actionButton.setImageResource(R.drawable.ic_pause_icon)
        viewModel.startTimer(totalTimeInSeconds)
        startSound()
    }

    private fun updateCountDownTimerUI() {
        binding.timeTV.text = secondsRemainingInString
        binding.progressCountdown.progress =
            (currentSecondsRemaining.toDouble() / totalTimeInSeconds.toDouble() * 100).toInt()


    }

    private fun initObserver() {
        viewModel.remainingTime.observe(this) {
            this.secondsRemainingInString = secondsInLongToStringFormat(it)
            currentSecondsRemaining = it

            updateCountDownTimerUI()
        }
    }

    private fun secondsInLongToStringFormat(seconds: Long): String {
        var tempSeconds = seconds
        val minutes = seconds / 60
        tempSeconds -= 60 * minutes
        val secondsToString = if (tempSeconds < 10) "0$tempSeconds" else tempSeconds.toString()

        return "$minutes:$secondsToString"
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
        meditationName = meditation.header
        meditationSound = meditation.soundResourceId

        val meditationSoundsList = getBackgroundSounds()
        var backgroundSoundName = ""
        for (backgroundSound in meditationSoundsList) {
            if (backgroundSound.sound == meditationSound) {
                backgroundSoundName = backgroundSound.name
            }
        }

        binding.currentBackgroundSoundButton.text = backgroundSoundName

    }

    private fun startTimer() {
        viewModel.startTimer(totalTimeInSeconds)
    }

    override fun sendPickedBackgroundSound(backgroundSound: BackgroundSound) {
        meditationSound = backgroundSound.sound
        binding.currentBackgroundSoundButton.text = backgroundSound.name

        stopSound()
        startSound()
    }

    override fun sendUserChoiceFromFragmentDialog(userChoice: Boolean) {
        this.isMeditationNeedToBeEnded = userChoice
    }

    override fun userChoiceFragmentDialogDismissed(isDismissedByCrossButton: Boolean) {
        if (!isDismissedByCrossButton) {
            if (isMeditationNeedToBeEnded) {
                stopSound()
                super.onBackPressed()
            }
        }
    }

}


