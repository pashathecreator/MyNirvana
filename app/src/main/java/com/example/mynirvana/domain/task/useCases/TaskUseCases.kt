package com.example.mynirvana.domain.task.useCases

data class TaskUseCases(
    val getTasksByDateUseCase: GetTasksByDateUseCase,
    val addTaskUseCase: AddTaskUseCase,
    val deleteCaseUseCase: DeleteTaskUseCase
)