package com.skelrath.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.ActivityPomodoroCreatorBinding
import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import com.skelrath.mynirvana.presentation.bottomSheets.quantityOfCirclesFragment.QuantityOfCirclesChoiceFragment
import com.skelrath.mynirvana.presentation.bottomSheets.timeChoiceFragment.TimeChoiceForPomodoroCreatorFragment
import com.skelrath.mynirvana.presentation.dialogs.pomodoro.savePomodoroAndStartDialog.SavePomodoroAndStartFragment
import com.skelrath.mynirvana.presentation.dialogs.pomodoro.startPomodoroWithoutSavingDialog.StartPomodoroWithoutSavingFragment
import com.skelrath.mynirvana.presentation.timeConvertor.TimeWorker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PomodoroCreatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPomodoroCreatorBinding
    private val viewModel: PomodoroCreatorViewModel by viewModels()

    private lateinit var currentButtonForBottomSheet: Button

    private var minutesForWorkingTime: Int = 0
    private var secondsForWorkingTime: Int = 0

    private var minutesForRelaxingTime: Int = 0
    private var secondsForRelaxingTime: Int = 0

    private var quantityOfCircles: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPomodoroCreatorBinding.inflate(layoutInflater)
        initButtons()

        setContentView(binding.root)
    }

    private fun initButtons() {

        with(binding) {
            backToFragmentButton.setOnClickListener {
                finish()
            }

            workingTimeButton.setOnClickListener {
                currentButtonForBottomSheet = it as Button
                openTimeChoice()
            }

            relaxingTimeButton.setOnClickListener {
                currentButtonForBottomSheet = it as Button
                openTimeChoice()
            }

            quantityOfCirclesButton.setOnClickListener {
                currentButtonForBottomSheet = it as Button
                openQuantityOfCirclesChoice()
            }

            startCurrentPomodoroTimer.setOnClickListener {
                openStartPomodoroDialog()
            }

            saveCurrentPomodoro.setOnClickListener {
                openSavePomodoroDialog()
            }
        }
    }

    private fun openTimeChoice() {
        TimeChoiceForPomodoroCreatorFragment().also {
            it.provideLambdaCallback { minutes: Int, seconds: Int ->
                currentButtonForBottomSheet.text =
                    TimeWorker.convertTimeFromMinutesAndSecondsToMinutesFormat(minutes, seconds)

                with(binding) {
                    when (currentButtonForBottomSheet) {
                        this.workingTimeButton -> {
                            this@PomodoroCreatorActivity.minutesForWorkingTime = minutes
                            this@PomodoroCreatorActivity.secondsForWorkingTime = seconds
                        }

                        this.relaxingTimeButton -> {
                            this@PomodoroCreatorActivity.minutesForRelaxingTime = minutes
                            this@PomodoroCreatorActivity.secondsForRelaxingTime = seconds
                        }
                    }
                }
            }

            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun openQuantityOfCirclesChoice() {
        QuantityOfCirclesChoiceFragment().also {
            it.provideLambdaCallback { quantityOfCircles: Int ->
                currentButtonForBottomSheet.text = quantityOfCircles.toString()
            }

            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun getPomodoroTimerName(): String = binding.pomodoroNameInputEditText.text.toString()

    private fun deserializePomodoro(): Pomodoro {
        var name = getPomodoroTimerName()
        var workTimeInSeconds = TimeWorker.convertMinutesAndSecondsToSeconds(
            minutesForWorkingTime,
            secondsForWorkingTime
        )
        var relaxTimeInSeconds = TimeWorker.convertMinutesAndSecondsToSeconds(
            minutesForRelaxingTime,
            secondsForRelaxingTime
        )
        var quantityOfCircles = quantityOfCircles
        val backGroundImages = arrayOf(
            R.drawable.ic_rectangle_blue,
            R.drawable.ic_rectangle_dark_blue,
            R.drawable.ic_rectangle_green,
            R.drawable.ic_rectangle_orange,
            R.drawable.ic_rectangle_orange,
            R.drawable.ic_rectangle_purple,
            R.drawable.ic_rectangle_red
        )
        val backgroundImage = backGroundImages.random()

        if (name.isEmpty())
            name = "Без названия"

        if (workTimeInSeconds == 0L)
            workTimeInSeconds = 1500

        if (relaxTimeInSeconds == 0L)
            relaxTimeInSeconds = 300

        if (quantityOfCircles == 0)
            quantityOfCircles = 4


        return Pomodoro(
            name,
            workTimeInSeconds,
            relaxTimeInSeconds,
            quantityOfCircles,
            backgroundImage,
            true
        )
    }

    private fun openSavePomodoroDialog() {
        savePomodoroTimer(deserializePomodoro())
        SavePomodoroAndStartFragment().also {
            it.provideLambdaCallback { userChoice: Boolean ->
                if (userChoice)
                    askToStartPomodoroTimerActivity(deserializePomodoro())
                else
                    finish()
            }
            it.isCancelable = false
            it.show(supportFragmentManager, it.tag)
        }
    }


    private fun openStartPomodoroDialog() {
        StartPomodoroWithoutSavingFragment().also {
            it.provideLambdaCallback { userChoice: Boolean ->
                val pomodoro = deserializePomodoro()
                if (userChoice)
                    viewModel.savePomodoroTimer(pomodoro)
                askToStartPomodoroTimerActivity(pomodoro)
                finish()
            }
            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun savePomodoroTimer(pomodoro: Pomodoro) {
        viewModel.savePomodoroTimer(pomodoro)
    }

    private fun askToStartPomodoroTimerActivity(pomodoro: Pomodoro) {
        Intent().also {
            it.putExtra("POMODORO_TO_START", pomodoro)
            setResult(RESULT_OK, it)
        }
        finish()
    }
}