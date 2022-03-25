package com.example.mynirvana.di

import com.example.mynirvana.data.meditation.repository.MeditationButtonRepositoryImpl
import com.example.mynirvana.domain.meditationButtons.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun providesMeditationButtonUseCases
                (meditationButtonRepositoryImpl: MeditationButtonRepositoryImpl): MeditationUseCases =
        MeditationUseCases(
            addMeditationButtonUseCase = AddMeditationUseCase(meditationButtonRepositoryImpl),
            deleteMeditationButtonUseCase = DeleteMeditationUseCase(
                meditationButtonRepositoryImpl
            ),
            getMeditationButtonByIdUseCase = GetMeditationByIdUseCase(
                meditationButtonRepositoryImpl
            ),
            getMeditationButtonsUseCase = GetMeditationUseCase(meditationButtonRepositoryImpl)
        )

}