package com.example.mynirvana.domain.task.repository

import com.example.mynirvana.domain.task.model.Task
import kotlinx.coroutines.flow.Flow
import java.sql.Date


interface TaskRepository {

    suspend fun getTaskByDate(dateOfTask: Date): Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

}