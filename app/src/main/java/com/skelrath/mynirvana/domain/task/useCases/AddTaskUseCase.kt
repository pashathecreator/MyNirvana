package com.skelrath.mynirvana.domain.task.useCases

import com.skelrath.mynirvana.domain.task.model.Task
import com.skelrath.mynirvana.domain.task.repository.TaskRepository

class AddTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun invoke(task: Task) {
        taskRepository.insertTask(task)
    }
}