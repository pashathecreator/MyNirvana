package com.example.mynirvana.data.task.dataSource

import androidx.room.*
import com.example.mynirvana.domain.task.model.DateConverters
import com.example.mynirvana.domain.task.model.Task
import kotlinx.coroutines.flow.Flow
import java.sql.Date


@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}