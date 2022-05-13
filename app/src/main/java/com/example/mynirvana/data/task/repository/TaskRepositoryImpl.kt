package com.example.mynirvana.data.task.repository

import com.example.mynirvana.data.task.dataSource.TaskDao
import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.domain.task.repository.TaskRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import java.sql.Date
import java.util.*
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class TaskRepositoryImpl @Inject constructor(private val dao: TaskDao) : TaskRepository {

    override suspend fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}