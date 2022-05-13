package com.example.mynirvana.presentation.activities.tasks

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels

import com.example.mynirvana.databinding.ActivityTaskCreatorBinding
import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.domain.habit.model.Habit
import com.example.mynirvana.presentation.dialogs.habit.habitSaved.HabitSavedFragment
import com.example.mynirvana.presentation.timeConvertor.TimeWorker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Date
import java.util.Calendar


@AndroidEntryPoint
class TaskCreatorActivity : AppCompatActivity(), HabitSavedFragmentCallback {

    enum class TaskState {
        OneTime, Habit
    }

    private lateinit var binding: ActivityTaskCreatorBinding
    private val viewModel: TaskCreatorViewModel by viewModels()

    private var currentState: TaskState = TaskState.OneTime

    private var timeWhenTaskStarts: Long = 50400
    private var dateOfTask: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons() {
        with(binding) {
            backToProductivityFragmentButtonInCaseCreator.setOnClickListener {
                onBackPressed()
            }

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
        }.also { picker ->

            picker.datePicker.minDate = calendar.time.time

            picker.setOnDismissListener {
                if (dateOfTask?.let { it1 -> TimeWorker.checkIsProvidedDateIsToday(it1) } == true)
                    binding.dateOfTaskButton.text = "Cегодня"
                else
                    binding.dateOfTaskButton.text =
                        dateOfTask?.let { it1 -> TimeWorker.convertTimeToDayOfMonthAndMonth(it1) }

            }

            picker.show()
        }


    }

    private fun openTimePickerBottomSheet() {
        val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
            .setMinute(0).setTitleText("Выберите время дела").build()
        picker.show(supportFragmentManager, picker.tag)

        picker.addOnPositiveButtonClickListener {
            timeWhenTaskStarts = (picker.hour * 3600 + picker.minute * 60).toLong()
            binding.timeOfTaskButton.text =
                TimeWorker.convertSecondsTo24HoursFormat(timeWhenTaskStarts)
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
        openHabitSavedDialog()
    }

    private fun openHabitSavedDialog() {
        HabitSavedFragment().also {
            it.provideCallback(this)
            it.provideHabitName(deserializeHabit().name)
            it.isCancelable = false
            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun deserializeHabit() =
        Habit(
            name = if (binding.taskNameInputEditText.text.toString() !=
                ""
            ) binding.taskNameInputEditText.text.toString() else
                "Без названия",
            habitDate = Date(Calendar.getInstance().time.time)
        )


    private fun deserializeTask() = Task(
        name = if (binding.taskNameInputEditText.text.toString() !=
            ""
        ) binding.taskNameInputEditText.text.toString() else
            "Без названия",
        timeWhenTaskStarts = timeWhenTaskStarts,
        dateOfTask = dateOfTask ?: Date(Calendar.getInstance().time.time)
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

    override fun onHabitSavedFragmentDismiss() {
        onBackPressed()
    }
}
