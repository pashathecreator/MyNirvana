package com.example.mynirvana.domain.case.useCases

import com.example.mynirvana.domain.case.model.Case
import com.example.mynirvana.domain.case.repository.CaseRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class GetCasesByDateUseCase(private val caseRepository: CaseRepository) {
    suspend fun invoke(date: Date): Flow<List<Case>> {
        return caseRepository.getCasesByDate(date)
    }
}