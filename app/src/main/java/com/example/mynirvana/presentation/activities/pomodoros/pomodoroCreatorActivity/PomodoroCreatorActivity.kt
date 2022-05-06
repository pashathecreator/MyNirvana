package com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import androidx.activity.viewModels
import com.example.mynirvana.databinding.ActivityPomodoroCreatorBinding
import com.example.mynirvana.presentation.bottomSheets.quantityOfCirclesFragment.QuantityOfCirclesChoiceFragment
import com.example.mynirvana.presentation.bottomSheets.timeChoiceFragment.TimeChoiceFragmentForMeditationCreatorActivity
import com.example.mynirvana.presentation.bottomSheets.timeChoiceFragment.TimeChoiceFragmentForPomodoroCreatorActivity
import com.example.mynirvana.presentation.timeConvertor.TimeConvertor

class PomodoroCreatorActivity : AppCompatActivity(), PomodoroCreatorActivityCallback {
    private lateinit var binding: ActivityPomodoroCreatorBinding
    private val viewModel: PomodoroCreatorViewModel by viewModels()

    private lateinit var currentButtonForBottomSheet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPomodoroCreatorBinding.inflate(layoutInflater)

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
    }

    override fun sendQuantityOfCircles(quantity: Int) {
        currentButtonForBottomSheet.text = quantity.toString()
    }

    private fun openQuantityOfCirclesChoice() {
        QuantityOfCirclesChoiceFragment(this).also {
            it.show(supportFragmentManager, it.tag)
        }
    }
}