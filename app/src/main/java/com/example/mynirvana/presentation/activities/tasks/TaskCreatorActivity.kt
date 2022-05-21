package com.example.mynirvana.presentation.activities.tasks

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.activity.viewModels

import com.example.mynirvana.databinding.ActivityTaskCreatorBinding
import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.domain.habit.model.Habit
import com.example.mynirvana.domain.notification.NotificationIdCreator
import com.example.mynirvana.domain.notification.broadcastReceiver.NotificationBroadcastReceiver
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
    private var timeWhenNotificationAlarming: Long = 46800
    private var dateOfTask: Date = Date(Calendar.getInstance().time.time)
    private var dateOfNotification: Date = Date(Calendar.getInstance().time.time)
    private var notificationId = 0

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
                openTimePickerDialogForTimeOfTask()
            }

            dateOfTaskButton.setOnClickListener {
                openDatePickerDialogForDateOfTask()
            }

            isNotificationNecessaryButton.setOnClickListener {
                checkTypeOfNotificationsNecessity()
            }

            timeOfNotificationButton.setOnClickListener {
                openTimePickerDialogForNotificationForTask()
            }

            dateOfNotificationButton.setOnClickListener {
                openDatePickerDialogForDateOfNotification()
            }

            saveTaskButton.setOnClickListener {
                checkTypeOfNotificationsNecessityAndCreateNotificationIfNecessary()
                checkTypeOfTaskAndSave()
            }
        }
    }


    private fun checkTypeOfNotificationsNecessityAndCreateNotificationIfNecessary() {
        if (currentNotificationState == NotificationNecessityState.Necessary) {
            when (currentTaskState) {
                TaskState.OneTime -> scheduleNotificationForTask(deserializeTask())
                TaskState.Habit -> scheduleNotificationForHabit(deserializeHabit())
            }
        }
    }

    private fun scheduleNotificationForTask(task: Task) {
        val intent = Intent(applicationContext, NotificationBroadcastReceiver::class.java)
        val title = "Напоминание о запланированном деле"
        val message = "На сегодняшний день вы запланировали \"${task.name}\" на ${
            TimeWorker.convertSecondsTo24HoursFormat(task.timeWhenTaskStarts)
        } "

        intent.putExtra(NotificationBroadcastReceiver.titleExtra, title)
        intent.putExtra(NotificationBroadcastReceiver.messageExtra, message)

        intent.putExtra(NotificationBroadcastReceiver.notificationIdExtra, task.id)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = dateOfNotification.time + timeWhenNotificationAlarming

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time, pendingIntent
        )

        showAlert(title, message)
    }

    private fun scheduleNotificationForHabit(habit: Habit) {
        val intent = Intent(applicationContext, NotificationBroadcastReceiver::class.java)
        val title = "Напоминание о вашей привычке"
        val message = "Не забудьте сделать \"${habit.name}\""

        intent.putExtra(NotificationBroadcastReceiver.titleExtra, title)
        intent.putExtra(NotificationBroadcastReceiver.messageExtra, message)

        intent.putExtra(NotificationBroadcastReceiver.notificationIdExtra, habit.id)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = dateOfNotification.time + timeWhenNotificationAlarming

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time, pendingIntent
        )

        showAlert(title, message)
    }


    private fun showAlert(title: String, message: String) =
        AlertDialog.Builder(applicationContext).setTitle(title).setMessage(message).show()


    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val description = "A Description of channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NotificationBroadcastReceiver.channelID, name, importance)
        channel.description = description

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun checkTypeOfNotificationsNecessity() {
        when (currentNotificationState) {
            NotificationNecessityState.Necessary -> {
                currentNotificationState = NotificationNecessityState.NotNecessary

                with(binding) {
                    isNotificationNecessaryButton.text = "Нет"
                    timeOfNotificationButton.visibility = View.GONE
                    timeOfNotificationTV.visibility = View.GONE
                    dateOfNotificationButton.visibility = View.GONE
                    dateOfNotificationTV.visibility = View.GONE
                }
            }

            NotificationNecessityState.NotNecessary -> {
                currentNotificationState = NotificationNecessityState.Necessary

                with(binding) {
                    isNotificationNecessaryButton.text = "Да"
                    timeOfNotificationButton.visibility = View.VISIBLE
                    timeOfNotificationTV.visibility = View.VISIBLE
                    if (currentTaskState == TaskState.OneTime) {
                        dateOfNotificationButton.visibility = View.VISIBLE
                        dateOfNotificationTV.visibility = View.VISIBLE
                    } else {
                        dateOfNotificationButton.visibility = View.GONE
                        dateOfNotificationTV.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun openDatePickerDialogForDateOfTask() {
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

    private fun openDatePickerDialogForDateOfNotification() {
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
                    dateOfNotification = Date(calendar.time.time)
                },
                todayDay,
                todayMonth,
                todayYear
            )
        } else {
            TODO("VERSION.SDK_INT < N")
        }.also { picker ->

            picker.datePicker.minDate = calendar.time.time
            picker.datePicker.maxDate = dateOfTask.time

            picker.setOnDismissListener {
                if (TimeWorker.checkIsProvidedDateIsToday(dateOfNotification))
                    binding.dateOfNotificationButton.text = "Cегодня"
                else
                    binding.dateOfNotificationButton.text =
                        TimeWorker.convertTimeToDayOfMonthAndMonth(dateOfNotification)
            }

            picker.show()
        }
    }

    private fun openTimePickerDialogForTimeOfTask() {
        val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
            .setMinute(0).setTitleText("Выберите время дела").build()
        picker.show(supportFragmentManager, picker.tag)

        picker.addOnPositiveButtonClickListener {
            timeWhenTaskStarts = (picker.hour * 3600 + picker.minute * 60).toLong()
            binding.timeOfTaskButton.text =
                TimeWorker.convertSecondsTo24HoursFormat(timeWhenTaskStarts)
        }
    }

    private fun openTimePickerDialogForNotificationForTask() {
        val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
            .setMinute(0).setTitleText("Выберите время напоминания").build()
        picker.show(supportFragmentManager, picker.tag)

        picker.addOnPositiveButtonClickListener {
            timeWhenNotificationAlarming = (picker.hour * 3600 + picker.minute * 60).toLong()
            binding.timeOfNotificationButton.text =
                TimeWorker.convertSecondsTo24HoursFormat(timeWhenNotificationAlarming)
        }
    }

    private fun checkTypeOfTaskAndSave() {
        when (currentTaskState) {
            TaskState.OneTime -> saveTask()
            TaskState.Habit -> saveHabit()
        }
    }

    private fun saveTask() {
        val task = deserializeTask()
        if (currentNotificationState == NotificationNecessityState.Necessary) {
            createNotificationChannel()
            scheduleNotificationForTask(task)
        }

        viewModel.saveTask(task)
        openTaskSavedDialog()
    }


    private fun saveHabit() {
        val habit = deserializeHabit()
        if (currentNotificationState == NotificationNecessityState.Necessary) {
            createNotificationChannel()
            scheduleNotificationForHabit(habit)
        }

        viewModel.saveHabit(habit)
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
            habitDate = Date(Calendar.getInstance().time.time),
            notificationTime = if (currentNotificationState == NotificationNecessityState.Necessary) timeWhenNotificationAlarming
            else null
        )


    private fun deserializeTask() = Task(
        name = if (binding.taskNameInputEditText.text.toString() !=
            ""
        ) binding.taskNameInputEditText.text.toString() else
            "Без названия",
        timeWhenTaskStarts = timeWhenTaskStarts,
        dateOfTask = dateOfTask,
        notificationDate = if (currentNotificationState == NotificationNecessityState.Necessary) dateOfNotification
        else null,
        notificationTime = if (currentNotificationState == NotificationNecessityState.Necessary) timeWhenNotificationAlarming
        else null
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
                    dateOfNotificationTV.visibility = View.GONE
                    dateOfNotificationButton.visibility = View.GONE
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
                    dateOfNotificationTV.visibility = View.VISIBLE
                    dateOfNotificationButton.visibility = View.VISIBLE
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
