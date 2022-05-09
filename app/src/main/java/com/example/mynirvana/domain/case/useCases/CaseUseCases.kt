package com.example.mynirvana.domain.case.useCases

data class CaseUseCases(
    val getCasesByDateUseCase: GetCasesByDateUseCase,
    val addCaseUseCase: AddCaseUseCase,
    val deleteCaseUseCase: DeleteCaseUseCase
)