package com.skelrath.mynirvana.presentation.activities.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.domain.habit.model.HabitForRealTimeDatabase
import com.skelrath.mynirvana.domain.habit.useCases.HabitUseCases
import com.skelrath.mynirvana.domain.task.model.Task
import com.skelrath.mynirvana.domain.task.model.TaskForRealTimeDatabase
import com.skelrath.mynirvana.domain.task.useCases.TaskUseCases
import com.skelrath.mynirvana.presentation.fireBaseConstants.FireBaseConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskCreatorViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val habitsUseCase: HabitUseCases
) :
    ViewModel() {

    fun saveTask(task: Task) {
        viewModelScope.launch {
            taskUseCases.addTaskUseCase.invoke(task)
            saveTaskToRealTimeDatabase(task)
        }
    }

    fun saveHabit(habit: Habit) {
        viewModelScope.launch {
            habitsUseCase.addHabitUseCase.invoke(habit)
            saveHabitToRealTimeDatabase(habit)
        }
    }

    private val currentUser = FirebaseAuth.getInstance().currentUser!!
    private val databaseReference = FirebaseDatabase.getInstance().reference

    private fun saveTaskToRealTimeDatabase(task: Task) {
        databaseReference.child(FireBaseConstants.USERS).child(currentUser.uid)
            .child(FireBaseConstants.TASKS).child(task.fireBaseId.toString())
            .setValue(TaskForRealTimeDatabase.convertTaskIntoTaskForRealTimeDatabase(task))
    }

    private fun saveHabitToRealTimeDatabase(habit: Habit) {
        databaseReference.child(FireBaseConstants.USERS).child(currentUser.uid)
            .child(FireBaseConstants.HABITS).child(habit.fireBaseId.toString())
            .setValue(HabitForRealTimeDatabase.convertHabitIntoHabitForRealTimeDatabase(habit))
    }
}