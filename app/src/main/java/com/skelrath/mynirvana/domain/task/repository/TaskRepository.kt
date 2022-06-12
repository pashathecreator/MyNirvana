package com.skelrath.mynirvana.domain.task.repository

import com.skelrath.mynirvana.domain.task.model.Task
import kotlinx.coroutines.flow.Flow


interface TaskRepository {

    suspend fun getTasks(): Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

}