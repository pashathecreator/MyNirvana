package com.example.mynirvana.domain.case.repository

import com.example.mynirvana.domain.case.model.Case
import kotlinx.coroutines.flow.Flow
import java.sql.Date


interface CaseRepository {

    suspend fun getCasesByDate(dateOfCase: Date): Flow<List<Case>>

    suspend fun insertCase(case: Case)

    suspend fun deleteCase(case: Case)

}