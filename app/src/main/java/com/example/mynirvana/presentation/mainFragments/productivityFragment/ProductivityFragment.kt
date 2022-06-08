package com.example.mynirvana.presentation.mainFragments.productivityFragment

import android.app.Activity
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.FragmentProductivityBinding
import com.example.mynirvana.domain.habit.model.Habit
import com.example.mynirvana.domain.notification.broadcastReceiver.NotificationBroadcastReceiver
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity.PomodoroCreatorActivity
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity.PomodoroTimerActivity
import com.example.mynirvana.presentation.activities.tasks.TaskCreatorActivity
import com.example.mynirvana.presentation.dialogs.pomodoro.startPomodoroDialog.StartPomodoroFragment
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeleteFragment
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import com.example.mynirvana.presentation.recycler.adapters.habit.HabitRecyclerAdapter
import com.example.mynirvana.presentation.recycler.adapters.pomodoro.PomodoroRecyclerAdapter
import com.example.mynirvana.presentation.recycler.adapters.task.TaskRecyclerAdapter
import com.example.mynirvana.presentation.recycler.onClickListeners.habits.HabitOnClickListener
import com.example.mynirvana.presentation.recycler.onClickListeners.itemTouchHelper.MyItemTouchHelper
import com.example.mynirvana.presentation.recycler.onClickListeners.pomodoros.PomodoroOnClickListener
import com.example.mynirvana.presentation.recycler.onClickListeners.tasks.TaskOnClickListener
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import com.example.mynirvana.presentation.timeConvertor.TimeWorker
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Date
import java.util.*

@AndroidEntryPoint
class ProductivityFragment : Fragment() {

    private lateinit var binding: FragmentProductivityBinding
    private val viewModel: ProductivityViewModel by viewModels()

    private lateinit var readyPomodorosData: List<Pomodoro>
    private lateinit var userPomodorosData: List<Pomodoro>
    private lateinit var tasksAdapter: TaskRecyclerAdapter
    private lateinit var habitsAdapter: HabitRecyclerAdapter
    private lateinit var userPomodorosAdapter: PomodoroRecyclerAdapter
    private var dateOfTask: Date = Date(Calendar.getInstance().time.time)

    private var pomodoroToStart: Pomodoro? = null
    private var pomodoroToDelete: Pomodoro? = null

    private var launcherForPomodoroTimerActivity: ActivityResultLauncher<Intent>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductivityBinding.inflate(inflater)
        initRecyclerView()
        initButtons()
        initPomodoroTimerActivityLauncher()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            chooseDataForTasksButton.setOnClickListener {
                startDatePickerDialogAndGetTaskOnChosenDate()
            }

            createTaskOrHabitButton.setOnClickListener {
                startTaskCreatorActivity()
            }

            createPomodoroTimerButton.setOnClickListener {
                startPomodoroCreatorActivity()
            }
        }
    }

    private fun startDatePickerDialogAndGetTaskOnChosenDate() {
        val calendar = Calendar.getInstance()
        val todayDay = calendar.get(Calendar.DAY_OF_MONTH)
        val todayMonth = calendar.get(Calendar.MONTH)
        val todayYear = calendar.get(Calendar.YEAR)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog(
                requireContext(),
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
                    binding.taskDateTV.text = "Cегодняшние дела"
                else
                    binding.taskDateTV.text =
                        "Дела на ${TimeWorker.convertTimeToDayOfMonthAndMonth(dateOfTask)}"

                getTaskByCurrentDate()
            }

            picker.show()
        }
    }

    private fun getTaskByCurrentDate() {
        viewModel.getTasksOnCurrentDate(dateOfTask)
    }

    private fun initPomodoroTimerActivityLauncher() {
        launcherForPomodoroTimerActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    pomodoroToStart =
                        result.data?.getSerializableExtra("POMODORO_TO_START") as Pomodoro?
                    pomodoroToStart?.let { startPomodoroTimerActivity(it) }
                }
            }
    }

    private fun startTaskCreatorActivity() {
        TaskCreatorActivity().also {
            Intent(activity, it::class.java).also { intent ->
                startActivity(intent)
            }
        }
    }

    private fun startPomodoroCreatorActivity() {
        PomodoroCreatorActivity().also {
            Intent(activity, it::class.java).also { intent ->
                launcherForPomodoroTimerActivity?.launch(intent)
            }
        }
    }


    private fun initRecyclerView() {
        with(binding) {
            tasksRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Vertical))
            }

            habitsRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Vertical))
            }

            readyPomodorosRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
            }

            userPomodorosRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
            }
        }

        addDataSetsToRecyclerViews()
    }

    private fun addDataSetsToRecyclerViews() {
        addDataSetToTasksRecycler()
        addDataSetToHabitRecycler()
        addDataSetToReadyPomodorosRecycler()
        addDataSetToUserPomodorosRecycler()
    }

    private fun addDataSetToHabitRecycler() {
        habitsAdapter =
            HabitRecyclerAdapter(
                object : HabitOnClickListener {
                    override fun onHabitComplete(habit: Habit) {
                        habit.isHabitCompleted = !habit.isHabitCompleted
                        habitsAdapter.notifyDataSetChanged()
                    }

                    override fun onHabitRemoved(habit: Habit) {
                        viewModel.deleteHabit(habit)
                    }
                }
            )



        viewModel.habitsLiveData.observe(viewLifecycleOwner) { habitsData ->
            checkIsBeen24HoursFromLastCompleteOfHabits(habitsData)

            habitsAdapter.submitListOfHabits(habitsData)

            val itemTouchHelper = ItemTouchHelper(MyItemTouchHelper(habitsAdapter))
            itemTouchHelper.attachToRecyclerView(binding.habitsRecycler)

            binding.habitsRecycler.adapter = habitsAdapter
        }
    }

//    private fun deleteNotificationForHabit(habit: Habit) {
//        val intent = Intent(requireContext(), NotificationBroadcastReceiver::class.java)
//        val pendingIntent =
//            habit.id?.let { PendingIntent.getBroadcast(requireContext(), it, intent, 0) }
//        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//        alarmManager.cancel(pendingIntent)
//        pendingIntent?.cancel()
//    }

    private fun checkIsBeen24HoursFromLastCompleteOfHabits(habitsData: List<Habit>) {
        for (habit in habitsData) {
            val calendar = Calendar.getInstance()
            calendar.time = habit.habitDate

            if (calendar.get(Calendar.DAY_OF_MONTH + 1) == Calendar.getInstance()
                    .get(Calendar.DAY_OF_MONTH)
            ) {
                habit.isHabitCompleted = false
                habit.habitDate = Calendar.getInstance().time as Date
            }
        }
    }


    private fun addDataSetToTasksRecycler() {
        tasksAdapter =
            TaskRecyclerAdapter(object : TaskOnClickListener {
                override fun onTaskComplete(task: Task) {
                    task.isTaskCompleted = !task.isTaskCompleted
                    tasksAdapter.notifyDataSetChanged()
                }

                override fun onTaskRemoved(position: Int) {
                    viewModel.deleteTask(position)
                }
            })

        viewModel.tasksLiveData.observe(viewLifecycleOwner) { tasksData ->
            tasksAdapter.submitListOfTasks(tasksData)

            val itemTouchHelper = ItemTouchHelper(MyItemTouchHelper(tasksAdapter))

            itemTouchHelper.attachToRecyclerView(binding.tasksRecycler)

            binding.tasksRecycler.adapter = tasksAdapter
        }
    }

    private fun deleteNotificationForTask(task: Task) {
        val intent = Intent(requireContext(), NotificationBroadcastReceiver::class.java)
        val pendingIntent =
            task.id?.let { PendingIntent.getBroadcast(requireContext(), it, intent, 0) }
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
        pendingIntent?.cancel()
    }

    private fun addDataSetToReadyPomodorosRecycler() {
        readyPomodorosData = viewModel.getReadyPomodoros()

        binding.readyPomodorosRecycler.adapter =
            PomodoroRecyclerAdapter(readyPomodorosData, object : PomodoroOnClickListener {
                override fun onPomodoroStart(pomodoro: Pomodoro) {
                    pomodoroToStart = pomodoro
                    openStartPomodoroDialog()
                }

                override fun onPomodoroDelete(pomodoro: Pomodoro) {
                    TODO("Ready pomodoros cannot be deleted")
                }

            })
    }

    private fun addDataSetToUserPomodorosRecycler() {
        viewModel.pomodorosLiveData.observe(viewLifecycleOwner) {
            userPomodorosData = it
            initUserHasZeroPomodorosTextView(userPomodorosData.isEmpty())

            userPomodorosAdapter =
                PomodoroRecyclerAdapter(it, object : PomodoroOnClickListener {
                    override fun onPomodoroStart(pomodoro: Pomodoro) {
                        pomodoroToStart = pomodoro
                        openStartPomodoroDialog()
                    }

                    override fun onPomodoroDelete(pomodoro: Pomodoro) {
                        pomodoroToDelete = pomodoro
                        openDeletePomodoroDialog()
                    }

                })
            binding.userPomodorosRecycler.adapter = userPomodorosAdapter
        }
    }


    private fun initUserHasZeroPomodorosTextView(isUserHasZeroPomodoros: Boolean) {
        if (isUserHasZeroPomodoros)
            binding.userHasZeroPomodoros.text =
                "Похоже, что вы еще не создали ни одного помодоро таймера"
        else binding.userHasZeroPomodoros.text =
            ""
    }

    private fun openDeletePomodoroDialog() {
        UserDeleteFragment().also { userDeleteFragment ->
            userDeleteFragment.provideLambdaCallback { userChoice ->
                if (userChoice)
                    pomodoroToDelete?.let { viewModel.deletePomodoro(it) }

                userPomodorosAdapter.notifyItemChanged(
                    userPomodorosData.indexOf(
                        pomodoroToDelete
                    )
                )

            }
            pomodoroToDelete?.let {
                userDeleteFragment.providePomodoro(it)
            }
            userDeleteFragment.show(parentFragmentManager, userDeleteFragment.tag)
        }
    }

    private fun openStartPomodoroDialog() {
        StartPomodoroFragment().also { startPomodoroFragment ->
            startPomodoroFragment.provideLambdaCallback { userChoice ->
                if (userChoice)
                    pomodoroToStart?.let { startPomodoroTimerActivity(it) }
            }
            pomodoroToStart?.let {
                startPomodoroFragment.providePomodoroName(it.name)
            }
            startPomodoroFragment.show(parentFragmentManager, startPomodoroFragment.tag)
        }
    }


    private fun startPomodoroTimerActivity(pomodoro: Pomodoro) {
        val intent = Intent(activity, PomodoroTimerActivity::class.java)
        intent.putExtra("POMODORO_INFO", pomodoro)

        startActivity(intent)
    }
}