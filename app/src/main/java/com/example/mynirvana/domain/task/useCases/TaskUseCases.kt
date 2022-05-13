package com.example.mynirvana.domain.task.useCases

data class TaskUseCases(
    val getTasksUseCase: GetTasksUseCase,
    val addTaskUseCase: AddTaskUseCase,
    val deleteCaseUseCase: DeleteTaskUseCase
)