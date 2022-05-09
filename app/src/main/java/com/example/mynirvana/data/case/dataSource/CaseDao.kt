package com.example.mynirvana.data.case.dataSource

import androidx.room.*
import com.example.mynirvana.domain.case.model.Case
import kotlinx.coroutines.flow.Flow
import java.sql.Date


@Dao
interface CaseDao {
    @Query("SELECT * FROM case WHERE dateOfCase = :dateOfCase")
    fun getCasesByDate(dateOfCase: Date): Flow<List<Case>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCase(case: Case)

    @Delete
    suspend fun deleteCase(case: Case)
}