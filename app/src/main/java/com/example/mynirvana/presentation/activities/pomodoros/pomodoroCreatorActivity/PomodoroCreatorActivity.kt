package com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityPomodoroCreatorBinding
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.presentation.bottomSheets.quantityOfCirclesFragment.QuantityOfCirclesChoiceFragment
import com.example.mynirvana.presentation.bottomSheets.timeChoiceFragment.TimeChoiceFragmentForPomodoroCreatorActivity
import com.example.mynirvana.presentation.dialogs.savePomodoroAndStartDialog.SavePomodoroAndStartFragment
import com.example.mynirvana.presentation.dialogs.startPomodoroWithoutSavingDialog.StartPomodoroWithoutSavingFragment
import com.example.mynirvana.presentation.mainFragments.productivityFragment.AskingToStartPomodoroTimer
import com.example.mynirvana.presentation.timeConvertor.TimeConvertor
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PomodoroCreatorActivity : AppCompatActivity(), PomodoroCreatorActivityCallback,
    SavePomodoroAndStartFragmentCallback, StartPomodoroWithoutSavingFragmentCallback {

    private lateinit var binding: ActivityPomodoroCreatorBinding
    private val viewModel: PomodoroCreatorViewModel by viewModels()

    private lateinit var currentButtonForBottomSheet: Button

    private var minutesForWorkingTime: Int = 0
    private var secondsForWorkingTime: Int = 0

    private var minutesForRelaxingTime: Int = 0
    private var secondsForRelaxingTime: Int = 0

    private var quantityOfCircles: Int = 0

    companion object {
        private lateinit var callback: AskingToStartPomodoroTimer
    }

    fun provideCallback(callback: AskingToStartPomodoroTimer) {
        PomodoroCreatorActivity.callback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPomodoroCreatorBinding.inflate(layoutInflater)
        initButtons()

        setContentView(binding.root)
    }

    private fun initButtons() {

        with(binding) {
            backToFragmentButton.setOnClickListener {
                onBackPressed()
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
        TimeChoiceFragmentForPomodoroCreatorActivity(this).also {
            it.show(supportFragmentManager, it.tag)
        }
    }

    override fun sendPickedTime(minutes: Int, seconds: Int) {
        currentButtonForBottomSheet.text =
            TimeConvertor.convertTimeFromMinutesAndSecondsToMinutesFormat(minutes, seconds)

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

    override fun sendQuantityOfCircles(quantity: Int) {
        currentButtonForBottomSheet.text = quantity.toString()
    }

    private fun openQuantityOfCirclesChoice() {
        QuantityOfCirclesChoiceFragment(this).also {
            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun getPomodoroTimerName(): String = binding.pomodoroNameInputEditText.text.toString()

    private fun deserializePomodoro(): Pomodoro {
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


        return Pomodoro(
            name = getPomodoroTimerName(),
            workTimeInSeconds = TimeConvertor.convertMinutesAndSecondsToSeconds(
                minutesForWorkingTime,
                secondsForWorkingTime
            ),
            relaxTimeInSeconds = TimeConvertor.convertMinutesAndSecondsToSeconds(
                minutesForRelaxingTime,
                secondsForRelaxingTime
            ),
            quantityOfCircles = quantityOfCircles,
            imageResourceId = backgroundImage,
        )
    }

    private fun openSavePomodoroDialog() {
        savePomodoroTimer(deserializePomodoro())
        SavePomodoroAndStartFragment().also {
            it.provideCallback(this)
            it.isCancelable = false
            it.show(supportFragmentManager, it.tag)
        }
    }

    override fun sendUserChoiceFromSavePomodoroAndStartFragment(userChoice: Boolean) {
        if (userChoice) {
            askToStartPomodoroTimerActivity(deserializePomodoro())
        } else {
            onBackPressed()
        }
    }

    private fun openStartPomodoroDialog() {
        StartPomodoroWithoutSavingFragment().also {
            it.provideCallback(this)
            it.show(supportFragmentManager, it.tag)
        }
    }

    override fun sendUserChoiceFromStartPomodoroWithoutSavingFragment(userChoice: Boolean) {
        if (userChoice) {
            viewModel.savePomodoroTimer(deserializePomodoro())
        }
        askToStartPomodoroTimerActivity(deserializePomodoro())
        onBackPressed()
    }


    private fun savePomodoroTimer(pomodoro: Pomodoro) {
        viewModel.savePomodoroTimer(pomodoro)
    }

    private fun askToStartPomodoroTimerActivity(pomodoro: Pomodoro) {
        callback.asksToStartPomodoroTimer(pomodoro)
        callback.onReadyToStartPomodoroTimer()
        onBackPressed()
    }


}