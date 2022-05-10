package com.example.mynirvana.presentation.mainFragments.productivityFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.FragmentProductivityBinding
import com.example.mynirvana.domain.habit.model.Habit
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity.PomodoroCreatorActivity
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity.PomodoroTimerActivity
import com.example.mynirvana.presentation.dialogs.startPomodoroDialog.StartPomodoroFragment
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeleteMeditationCallback
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeleteFragment
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeletePomodoroCallback
import com.example.mynirvana.presentation.mainFragments.productivityFragment.callback.AskingToStartPomodoroTimer
import com.example.mynirvana.presentation.mainFragments.productivityFragment.callback.PomodoroTimerStartCallback
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import com.example.mynirvana.presentation.recycler.adapters.habit.HabitRecyclerAdapter
import com.example.mynirvana.presentation.recycler.adapters.pomodoro.PomodoroRecyclerAdapter
import com.example.mynirvana.presentation.recycler.adapters.task.TaskRecyclerAdapter
import com.example.mynirvana.presentation.recycler.onClickListeners.habits.HabitOnClickListener
import com.example.mynirvana.presentation.recycler.onClickListeners.pomodoros.PomodoroOnClickListener
import com.example.mynirvana.presentation.recycler.onClickListeners.tasks.TaskOnClickListener
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductivityFragment : Fragment(), PomodoroTimerStartCallback, AskingToStartPomodoroTimer,
    UserDeletePomodoroCallback {

    private lateinit var binding: FragmentProductivityBinding
    private val viewModel: ProductivityViewModel by viewModels()

    private lateinit var tasksData: List<Task>
    private lateinit var habitsData: List<Habit>
    private lateinit var readyPomodorosData: List<Pomodoro>
    private lateinit var userPomodorosData: List<Pomodoro>

    //    private lateinit var tasksAdapter : TaskRecyclerAdapter
//    private lateinit var habitsAdapter : HabitRecyclerAdapter
    private lateinit var userPomodorosAdapter: PomodoroRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductivityBinding.inflate(inflater)
        initRecyclerView()
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            createPomodoroTimerButton.setOnClickListener {
                startPomodoroCreatorActivity()
            }
        }
    }

    private fun startPomodoroCreatorActivity() {
        PomodoroCreatorActivity().also {
            it.provideCallback(this)
            val intent = Intent(activity, it::class.java)
            startActivity(intent)
        }
    }

    private var pomodoroThatNeedToBeStarted: Pomodoro? = null

    override fun asksToStartPomodoroTimer(pomodoro: Pomodoro) {
        pomodoroThatNeedToBeStarted = pomodoro
    }

    override fun onReadyToStartPomodoroTimer() {
        pomodoroThatNeedToBeStarted?.let {
            startPomodoroTimerActivity(it)
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
        viewModel.habitsLiveData.observe(viewLifecycleOwner) {
            habitsData = it

            binding.habitsRecycler.adapter =
                HabitRecyclerAdapter(habitsData, object : HabitOnClickListener {
                    override fun onComplete(habit: Habit) {
                        habit.isHabitCompleted = !habit.isHabitCompleted
                    }

                })
        }
    }

    private fun addDataSetToTasksRecycler() {
        viewModel.tasksLiveData.observe(viewLifecycleOwner) {
            tasksData = it

            binding.tasksRecycler.adapter =
                TaskRecyclerAdapter(tasksData, object : TaskOnClickListener {
                    override fun onComplete(task: Task) {
                        task.isTaskCompleted = !task.isTaskCompleted
                    }
                })
        }
    }

    private var currentPomodoroToStart: Pomodoro? = null

    private fun addDataSetToReadyPomodorosRecycler() {
        readyPomodorosData = viewModel.getReadyPomodoros()

        binding.readyPomodorosRecycler.adapter =
            PomodoroRecyclerAdapter(readyPomodorosData, object : PomodoroOnClickListener {
                override fun onPomodoroStart(pomodoro: Pomodoro) {
                    currentPomodoroToStart = pomodoro
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
                        currentPomodoroToStart = pomodoro
                        openStartPomodoroDialog()
                    }

                    override fun onPomodoroDelete(pomodoro: Pomodoro) {
                        pomodoroThatNeedToBeDeleted = pomodoro
                        openDeletePomodoroDialog()
                    }

                })
            binding.userPomodorosRecycler.adapter = userPomodorosAdapter
        }
    }

    private var pomodoroThatNeedToBeDeleted: Pomodoro? = null

    private fun initUserHasZeroPomodorosTextView(isUserHasZeroPomodoros: Boolean) {
        if (isUserHasZeroPomodoros)
            binding.userHasZeroPomodoros.text =
                "Похоже, что вы еще не создали ни одного помодоро таймера"
        else binding.userHasZeroPomodoros.text =
            ""
    }

    private fun openDeletePomodoroDialog() {
        UserDeleteFragment().also { userDeleteFragment ->
            userDeleteFragment.provideCallbackForPomodoro(this)
            pomodoroThatNeedToBeDeleted?.let {
                userDeleteFragment.providePomodoro(it)
            }
            userDeleteFragment.show(parentFragmentManager, userDeleteFragment.tag)
        }
    }

    private fun openStartPomodoroDialog() {
        StartPomodoroFragment().also { startPomodoroFragment ->
            startPomodoroFragment.provideCallback(this)
            currentPomodoroToStart?.let {
                startPomodoroFragment.providePomodoroName(it.name)
            }
            startPomodoroFragment.show(parentFragmentManager, startPomodoroFragment.tag)
        }
    }

    private var isNeedToStartPomodoroTimerActivity: Boolean = false

    override fun sendUserChoiceFromStartPomodoroDialog(userChoice: Boolean) {
        isNeedToStartPomodoroTimerActivity = userChoice
    }

    override fun onPomodoroStartDialogDismissed() {
        if (isNeedToStartPomodoroTimerActivity)
            currentPomodoroToStart?.let { startPomodoroTimerActivity(it) }
    }

    private fun startPomodoroTimerActivity(pomodoro: Pomodoro) {
        val intent = Intent(activity, PomodoroTimerActivity::class.java)
        intent.putExtra("POMODORO_INFO", pomodoro)

        startActivity(intent)
    }

    override fun userDecidedAboutDeletingPomodoro(userChoice: Boolean) {
        if (userChoice)
            pomodoroThatNeedToBeDeleted?.let { viewModel.deletePomodoro(it) }

        userPomodorosAdapter.notifyItemChanged(userPomodorosData.indexOf(pomodoroThatNeedToBeDeleted))
    }


}