package com.example.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityPomodoroTimerBinding
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.presentation.activities.pomodoros.PomodoroTimerState
import com.example.mynirvana.presentation.activities.timerState.TimerState
import com.example.mynirvana.presentation.timeConvertor.TimeConvertor
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PomodoroTimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPomodoroTimerBinding
    private val viewModel: PomodoroTimerViewModel by viewModels()
    private lateinit var providedPomodoro: Pomodoro

    private var totalSecondsRemainingForCurrentState: Long = -1
    private var secondsRemaining: Long = 0

    private var currentTimerState: TimerState = TimerState.Playing
    private var currentPomodoroTimerState: PomodoroTimerState = PomodoroTimerState.Work

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPomodoroTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deserializePomodoro()

        initTimerObserver()
        initPomodoroTimerStateObserver()
        initButtons()
    }

    private fun deserializePomodoro() {
        providedPomodoro = intent.getSerializableExtra("POMODORO_INFO") as Pomodoro
        totalSecondsRemainingForCurrentState = providedPomodoro.workTimeInSeconds
    }

    private fun initTimerObserver() {
        viewModel.remainingTime.observe(this) {
            if (totalSecondsRemainingForCurrentState == -1L) {
                totalSecondsRemainingForCurrentState = it
            }
            secondsRemaining = it
            updateCountDownTimerUI()
        }
    }

    private fun updateCountDownTimerUI() {
        binding.timeTV.text =
            TimeConvertor.convertTimeFromSecondsToMinutesFormatWithoutTimeWord(secondsRemaining)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.progressCountdownInPomodoroTimer.setProgress(
                (secondsRemaining.toDouble() / totalSecondsRemainingForCurrentState.toDouble() * 100).toInt(),
                true
            )
        }
    }

    private fun initPomodoroTimerStateObserver() {
        viewModel.pomodoroTimerState.observe(this) {
            currentPomodoroTimerState = it
            binding.currentStateOfPomodoroTimerTV.text = convertPomodoroTimerStateToString()
            changeValueSecondsBasedOnCurrentPomodoroTimerState()
        }
    }

    private fun changeValueSecondsBasedOnCurrentPomodoroTimerState() {
        totalSecondsRemainingForCurrentState = when (currentPomodoroTimerState) {
            PomodoroTimerState.Work -> providedPomodoro.workTimeInSeconds
            PomodoroTimerState.Relax -> providedPomodoro.relaxTimeInSeconds
        }
    }

    private fun convertPomodoroTimerStateToString(): String = when (currentPomodoroTimerState) {
        PomodoroTimerState.Work -> "Работа"
        PomodoroTimerState.Relax -> "Отдых"
    }

    private fun convertPomodoroTimerStateToStringWithCase(): String =
        when (currentPomodoroTimerState) {
            PomodoroTimerState.Work -> "Работу"
            PomodoroTimerState.Relax -> "Отдых"
        }

    private fun initButtons() {
        with(binding) {
            actionButton.setOnClickListener {
                changeCurrentTimerStateAction()
                when (currentTimerState) {
                    TimerState.Paused -> pauseCountDownTimer()
                    TimerState.Playing -> playCountDownTimer()
                }
            }

            skipCurrentStateButton.text =
                "Пропустить ${convertPomodoroTimerStateToStringWithCase()}"

            skipCurrentStateButton.setOnClickListener {
                skipCurrentState()
            }
        }
    }

    private fun pauseCountDownTimer() {
        binding.actionButton.setImageResource(R.drawable.ic_play_icon)
        viewModel.stopTimer()
    }

    private fun playCountDownTimer() {
        binding.actionButton.setImageResource(R.drawable.ic_pause_icon)
        viewModel.startTimer(secondsRemaining)
    }


    private fun changeCurrentTimerStateAction() {
        currentTimerState =
            if (currentTimerState == TimerState.Playing) TimerState.Paused else TimerState.Playing
    }

    private fun skipCurrentState() {
        viewModel.skipCurrentState(getValueOfSecondsForNextPomodoroTimerState())
    }

    private fun getValueOfSecondsForNextPomodoroTimerState(): Long =
        when (currentPomodoroTimerState) {
            PomodoroTimerState.Work -> providedPomodoro.relaxTimeInSeconds
            PomodoroTimerState.Relax -> providedPomodoro.workTimeInSeconds
        }


}