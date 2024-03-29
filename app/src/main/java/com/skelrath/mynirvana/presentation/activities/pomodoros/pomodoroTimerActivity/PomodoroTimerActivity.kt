package com.skelrath.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.ActivityPomodoroTimerBinding
import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import com.skelrath.mynirvana.presentation.activities.pomodoros.PomodoroTimerState
import com.skelrath.mynirvana.presentation.activities.timerState.TimerState
import com.skelrath.mynirvana.presentation.dialogs.pomodoro.exitFromPomodoroDialog.ExitFromPomodoroFragment
import com.skelrath.mynirvana.presentation.dialogs.pomodoro.pomodoroTimerOnFinishDialog.PomodoroTimerOnFinishFragment
import com.skelrath.mynirvana.presentation.timeConvertor.TimeWorker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PomodoroTimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPomodoroTimerBinding
    private val viewModel: PomodoroTimerViewModel by viewModels()
    private lateinit var providedPomodoro: Pomodoro

    private var totalSecondsRemainingForCurrentState: Long = -1
    private var secondsRemaining: Long = 0

    private var currentCircle = 1
    private var totalQuantityOfCircles = 0

    private var currentTimerState: TimerState = TimerState.Playing
    private var currentPomodoroTimerState: PomodoroTimerState = PomodoroTimerState.Work

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPomodoroTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        deserializePomodoro()
        viewModel.initViewModel(
            providedPomodoro
        )
        initTimerObserver()
        initPomodoroTimerStateObserver()
        initCirclesObserver()
        initIsPomodoroCompletedObserver()
        initButtons()
        startTimer()
    }

    private fun initIsPomodoroCompletedObserver() {
        viewModel.isPomodoroCompleted.observe(this) {
            if (it)
                pomodoroTimerOnFinish()
        }
    }

    private fun initCirclesObserver() {
        viewModel.currentCircleState.observe(this) {
            currentCircle = it
            updateCirclesCounterUI()
        }
    }

    private fun updateCirclesCounterUI() {
        binding.circlesCounter.text = "$currentCircle/$totalQuantityOfCircles"
    }

    private fun startTimer() {
        viewModel.startTimer(totalSecondsRemainingForCurrentState)
    }

    private fun deserializePomodoro() {
        providedPomodoro = intent.getSerializableExtra("POMODORO_INFO") as Pomodoro
        totalSecondsRemainingForCurrentState = providedPomodoro.workTimeInSeconds!!
        totalQuantityOfCircles = providedPomodoro.quantityOfCircles!!
    }

    private fun initTimerObserver() {
        viewModel.remainingTime.observe(this) {
            secondsRemaining = it
            updateCountDownTimerUI()
        }
    }

    private fun updateCountDownTimerUI() {
        binding.timeTV.text =
            TimeWorker.convertTimeFromSecondsToMinutesFormatWithoutTimeWord(secondsRemaining)

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
            binding.skipCurrentStateButton.text =
                "Пропустить ${convertPomodoroTimerStateToStringWithCase()}"
            changeValueSecondsBasedOnCurrentPomodoroTimerState()
        }
    }

    private fun changeValueSecondsBasedOnCurrentPomodoroTimerState() {
        totalSecondsRemainingForCurrentState = when (currentPomodoroTimerState) {
            PomodoroTimerState.Work -> providedPomodoro.workTimeInSeconds!!
            PomodoroTimerState.Relax -> providedPomodoro.relaxTimeInSeconds!!
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
            backButtonInPomodoroTimer.setOnClickListener {
                onBackPressed()
            }

            actionButton.setOnClickListener {
                changeCurrentTimerStateAction()
                when (currentTimerState) {
                    TimerState.Paused -> pauseCountDownTimer()
                    TimerState.Playing -> playCountDownTimer()
                }
            }

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

    private fun skipCurrentState() = viewModel.skipCurrentState()


    private fun pomodoroTimerOnFinish() {
        if (!PomodoroTimerOnFinishFragment.isDialogResumed) {
            PomodoroTimerOnFinishFragment().also {
                it.provideLambdaCallback {
                    viewModel.stopEndSound()
                    finish()
                }
                it.isCancelable = false
                PomodoroTimerOnFinishFragment.isDialogResumed = true
                it.show(supportFragmentManager, it.tag)
            }
        }
    }

    override fun onBackPressed() {
        startExitFromPomodoroDialog()
    }

    private fun startExitFromPomodoroDialog() {
        ExitFromPomodoroFragment().also {
            it.provideLambdaCallback { userChoice ->
                if (userChoice) {

                    finish()
                }
            }
            it.show(supportFragmentManager, it.tag)
        }
    }
}