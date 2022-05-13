package com.example.mynirvana.domain.task.useCases

import com.example.mynirvana.domain.task.repository.TaskRepository

class GetTasksUseCase(private val taskRepository: TaskRepository) {
    suspend fun invoke() = taskRepository.getTasks()
}