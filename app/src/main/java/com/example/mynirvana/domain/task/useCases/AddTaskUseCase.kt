package com.example.mynirvana.domain.task.useCases

import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.domain.task.repository.TaskRepository

class AddTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun invoke(task: Task) {
        taskRepository.insertTask(task)
    }
}