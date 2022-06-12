package com.skelrath.mynirvana.domain.task.useCases

data class TaskUseCases(
    val getTasksUseCase: GetTasksUseCase,
    val addTaskUseCase: AddTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase
)