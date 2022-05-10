package com.example.mynirvana.domain.task.useCases

import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.domain.task.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import java.sql.Date

class GetTasksByDateUseCase(private val taskRepository: TaskRepository) {
    suspend fun invoke(date: Date): Flow<List<Task>> {
        return taskRepository.getTaskByDate(date)
    }
}