package com.skelrath.mynirvana.domain.task.useCases

import com.skelrath.mynirvana.domain.task.repository.TaskRepository

class GetTasksUseCase(private val taskRepository: TaskRepository) {
    suspend fun invoke() = taskRepository.getTasks()
}