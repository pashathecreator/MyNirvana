package com.example.mynirvana.presentation.activities.tasks

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mynirvana.databinding.ActivityCaseCreatorBinding
import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.domain.habit.model.Habit
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Date
import java.util.*


@AndroidEntryPoint
class TaskCreatorActivity : AppCompatActivity() {

    enum class TaskState {
        OneTime, Habit
    }

    private lateinit var binding: ActivityCaseCreatorBinding
    private val viewModel: TaskCreatorViewModel by viewModels()

    private var currentState: TaskState = TaskState.OneTime

    private var timeWhenTaskStarts: Long = 0
    private lateinit var dateOfTask: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaseCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons() {
        with(binding) {
            typeOfTaskButton.setOnClickListener {
                changeCaseState()
            }

            timeOfTaskButton.setOnClickListener {
                openTimePickerBottomSheet()
            }

            dateOfTaskButton.setOnClickListener {
                openDatePickerBottomSheet()
            }

            saveTaskButton.setOnClickListener {
                checkTypeOfTaskAndSave()
            }
        }
    }

    private fun openDatePickerBottomSheet() {
        val calendar = Calendar.getInstance()
        val todayDay = calendar.get(Calendar.DAY_OF_MONTH)
        val todayMonth = calendar.get(Calendar.MONTH)
        val todayYear = calendar.get(Calendar.YEAR)

        val picker = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    dateOfTask = Date(calendar.time.time)
                },
                todayDay,
                todayMonth,
                todayYear
            )
        } else {
            TODO("VERSION.SDK_INT < N")
        }

        picker.show()
    }

    private fun openTimePickerBottomSheet() {
        val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
            .setMinute(0).setTitleText("Выберите время дела").build()
        picker.show(supportFragmentManager, picker.tag)

        picker.addOnPositiveButtonClickListener {
            timeWhenTaskStarts = (picker.hour * 3600 + picker.minute * 60).toLong()
        }
    }

    private fun checkTypeOfTaskAndSave() {
        when (currentState) {
            TaskState.OneTime -> saveCase()
            TaskState.Habit -> saveHabit()
        }
    }

    private fun saveCase() {
        viewModel.saveTask(deserializeTask())
    }

    private fun saveHabit() {
        viewModel.saveHabit(deserializeHabit())
    }

    private fun deserializeHabit() =
        Habit(name = binding.taskNameEditText.text.toString())


    private fun deserializeTask() = Task(
        name = binding.taskNameEditText.text.toString(),
        timeWhenTaskStarts = timeWhenTaskStarts,
        dateOfTask = dateOfTask
    )

    private fun changeCaseState() {
        when (currentState) {
            TaskState.OneTime -> {
                currentState = TaskState.Habit
                with(binding) {
                    typeOfTaskButton.text = "Привычка"
                    timeOfTaskInCreatorTV.visibility = View.GONE
                    timeOfTaskButton.visibility = View.GONE
                    dateOfTaskTV.visibility = View.GONE
                    dateOfTaskButton.visibility = View.GONE
                }
            }

            TaskState.Habit -> {
                currentState = TaskState.OneTime
                with(binding) {
                    typeOfTaskButton.text = "Разовая"
                    timeOfTaskInCreatorTV.visibility = View.VISIBLE
                    timeOfTaskButton.visibility = View.VISIBLE
                    dateOfTaskTV.visibility = View.VISIBLE
                    dateOfTaskButton.visibility = View.VISIBLE
                }
            }

        }
    }
}
