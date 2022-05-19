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
import com.example.mynirvana.domain.notification.model.Notification
import com.example.mynirvana.presentation.dialogs.habit.habitSaved.HabitSavedFragment
import com.example.mynirvana.presentation.dialogs.task.TaskSavedFragment
import com.example.mynirvana.presentation.timeConvertor.TimeWorker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Date
import java.util.Calendar


@AndroidEntryPoint
class TaskCreatorActivity : AppCompatActivity(), HabitSavedFragmentCallback,
    TaskSavedFragmentCallback {

    enum class TaskState {
        OneTime, Habit
    }

    enum class NotificationNecessityState {
        Necessary, NotNecessary
    }

    private lateinit var binding: ActivityTaskCreatorBinding
    private val viewModel: TaskCreatorViewModel by viewModels()

    private var currentTaskState: TaskState = TaskState.OneTime
    private var currentNotificationState: NotificationNecessityState =
        NotificationNecessityState.Necessary

    private var timeWhenTaskStarts: Long = 50400
    private var dateOfTask: Date = Date(Calendar.getInstance().time.time)

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
                changeTaskState()
            }

            timeOfTaskButton.setOnClickListener {
                openTimePickerBottomSheet()
            }

            dateOfTaskButton.setOnClickListener {
                openDatePickerBottomSheet()
            }

            isNotificationNecessaryButton.setOnClickListener {
                checkTypeOfNotificationsNecessity()
            }

            saveTaskButton.setOnClickListener {
                checkTypeOfNotificationsNecessityAndCreateNotificationIfNecessary()
                checkTypeOfTaskAndSave()
            }
        }
    }

    private fun checkTypeOfNotificationsNecessityAndCreateNotificationIfNecessary() {
        val notification: Notification = when (currentTaskState) {
            TaskState.OneTime -> {
                val task = deserializeTask()
                Notification()
            }
            else -> {}
        }

        when (currentNotificationState) {
            NotificationNecessityState.Necessary -> {
            }
        }
    }

    private fun checkTypeOfNotificationsNecessity() {
        when (currentNotificationState) {
            NotificationNecessityState.Necessary -> {
                currentNotificationState = NotificationNecessityState.NotNecessary

                with(binding) {
                    timeOfNotificationButton.visibility = View.GONE
                    timeOfNotificationTV.visibility = View.GONE
                }
            }

            NotificationNecessityState.NotNecessary -> {
                currentNotificationState = NotificationNecessityState.Necessary

                with(binding) {
                    timeOfNotificationButton.visibility = View.VISIBLE
                    timeOfNotificationTV.visibility = View.VISIBLE
                }
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
                if (TimeWorker.checkIsProvidedDateIsToday(dateOfTask))
                    binding.dateOfTaskButton.text = "Cегодня"
                else
                    binding.dateOfTaskButton.text =
                        TimeWorker.convertTimeToDayOfMonthAndMonth(dateOfTask)
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
        when (currentTaskState) {
            TaskState.OneTime -> saveTask()
            TaskState.Habit -> saveHabit()
        }
    }

    private fun saveTask() {
        viewModel.saveTask(deserializeTask())
        openTaskSavedDialog()
    }


    private fun saveHabit() {
        viewModel.saveHabit(deserializeHabit())
        openHabitSavedDialog()
    }

    private fun openTaskSavedDialog() {
        TaskSavedFragment().also {
            val task = deserializeTask()
            it.provideCallback(this)
            it.provideHabitNameAndItsDataInStringFormat(
                task.name,
                TimeWorker.convertTimeToDayOfMonthAndMonth(task.dateOfTask)
            )
            it.isCancelable = false
            it.show(supportFragmentManager, it.tag)
        }
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
        dateOfTask = dateOfTask
    )

    private fun changeTaskState() {
        when (currentTaskState) {
            TaskState.OneTime -> {
                currentTaskState = TaskState.Habit
                with(binding) {
                    typeOfTaskButton.text = "Привычка"
                    timeOfTaskInCreatorTV.visibility = View.GONE
                    timeOfTaskButton.visibility = View.GONE
                    dateOfTaskTV.visibility = View.GONE
                    dateOfTaskButton.visibility = View.GONE
                }
            }

            TaskState.Habit -> {
                currentTaskState = TaskState.OneTime
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

    override fun onTaskSavedFragmentDismiss() {
        onBackPressed()
    }
}
