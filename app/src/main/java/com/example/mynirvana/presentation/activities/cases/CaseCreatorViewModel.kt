package com.example.mynirvana.presentation.activities.cases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.case.model.Case
import com.example.mynirvana.domain.case.useCases.CaseUseCases
import com.example.mynirvana.domain.habit.model.Habit
import com.example.mynirvana.domain.habit.useCases.GetHabitsUseCase
import com.example.mynirvana.domain.habit.useCases.HabitUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaseCreatorViewModel @Inject constructor(
    private val caseUseCases: CaseUseCases,
    private val habitsUseCase: HabitUseCases
) :
    ViewModel() {

    fun saveCase(case: Case) {
        viewModelScope.launch {
            caseUseCases.addCaseUseCase.invoke(case)
        }
    }

    fun saveHabit(habit: Habit) {
        viewModelScope.launch {
            habitsUseCase.addHabitUseCase.invoke(habit)
        }
    }
}