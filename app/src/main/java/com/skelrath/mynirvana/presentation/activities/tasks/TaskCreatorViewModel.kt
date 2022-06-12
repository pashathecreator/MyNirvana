package com.skelrath.mynirvana.presentation.activities.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.domain.habit.useCases.HabitUseCases
import com.skelrath.mynirvana.domain.task.model.Task
import com.skelrath.mynirvana.domain.task.useCases.TaskUseCases
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
        }
    }

    fun saveHabit(habit: Habit) {
        viewModelScope.launch {
            habitsUseCase.addHabitUseCase.invoke(habit)
        }
    }
}