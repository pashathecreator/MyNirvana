package com.example.mynirvana.presentation.activities.cases

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mynirvana.databinding.ActivityCaseCreatorBinding
import com.example.mynirvana.domain.case.model.Case
import com.example.mynirvana.domain.habit.model.Habit
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.sql.Date
import java.util.*

class CaseCreatorActivity : AppCompatActivity() {

    enum class CaseState {
        OneTime, Habit
    }

    private lateinit var binding: ActivityCaseCreatorBinding
    private val viewModel: CaseCreatorViewModel by viewModels()

    private var currentState: CaseState = CaseState.OneTime

    private var timeWhenCaseStarts: Long = 0
    private lateinit var dateOfCase: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaseCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons() {
        with(binding) {
            typeOfCaseButton.setOnClickListener {
                changeCaseState()
            }

            timeOfCaseButton.setOnClickListener {
                openTimePickerBottomSheet()
            }

            dateButton.setOnClickListener {
                openDatePickerBottomSheet()
            }

            saveCaseButton.setOnClickListener {
                checkTypeOfCaseAndSave()
            }
        }
    }

    private fun openDatePickerBottomSheet() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val picker = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    dateOfCase = Date(year, month, day)
                },
                day,
                month,
                year
            )
        } else {
            TODO("VERSION.SDK_INT < N")
        }
    }

    private fun openTimePickerBottomSheet() {
        val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
            .setMinute(0).setTitleText("Выберите время дела").build()
        picker.show(supportFragmentManager, picker.tag)

        picker.addOnPositiveButtonClickListener {
            timeWhenCaseStarts = (picker.hour * 3600 + picker.minute * 60).toLong()
        }
    }

    private fun checkTypeOfCaseAndSave() {
        when (currentState) {
            CaseState.OneTime -> saveCase()
            CaseState.Habit -> saveHabit()
        }
    }

    private fun saveCase() {
        viewModel.saveCase(deserializeCase())
    }

    private fun saveHabit() {
        viewModel.saveHabit(deserializeHabit())
    }

    private fun deserializeHabit() =
        Habit(name = binding.caseNameEditText.text.toString())


    private fun deserializeCase() = Case(
        name = binding.caseNameEditText.text.toString(),
        timeWhenCaseStarts = timeWhenCaseStarts,
        dateOfCase = dateOfCase
    )

    private fun changeCaseState() {
        when (currentState) {
            CaseState.OneTime -> {
                currentState = CaseState.Habit
                with(binding) {
                    typeOfCaseButton.text = "Привычка"
                    timeOfCaseInCreatorTV.visibility = View.GONE
                    timeOfCaseButton.visibility = View.GONE
                    dateOfCaseTV.visibility = View.GONE
                    dateButton.visibility = View.GONE
                }
            }

            CaseState.Habit -> {
                currentState = CaseState.OneTime
                with(binding) {
                    typeOfCaseButton.text = "Разовая"
                    timeOfCaseInCreatorTV.visibility = View.VISIBLE
                    timeOfCaseButton.visibility = View.VISIBLE
                    dateOfCaseTV.visibility = View.VISIBLE
                    dateButton.visibility = View.VISIBLE
                }
            }

        }
    }
}
