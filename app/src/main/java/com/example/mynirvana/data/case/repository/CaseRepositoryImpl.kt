package com.example.mynirvana.data.case.repository

import com.example.mynirvana.data.case.dataSource.CaseDao
import com.example.mynirvana.domain.case.model.Case
import com.example.mynirvana.domain.case.repository.CaseRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import java.sql.Date
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class CaseRepositoryImpl @Inject constructor(private val dao: CaseDao) : CaseRepository {

    override suspend fun getCasesByDate(dateOfCase: Date): Flow<List<Case>> {
        return dao.getCasesByDate(dateOfCase)
    }

    override suspend fun insertCase(case: Case) {
        dao.insertCase(case)
    }

    override suspend fun deleteCase(case: Case) {
        dao.deleteCase(case)
    }
}