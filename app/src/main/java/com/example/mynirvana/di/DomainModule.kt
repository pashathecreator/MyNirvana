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
                (meditationButtonRepositoryImpl: MeditationButtonRepositoryImpl): MeditationButtonUseCases =
        MeditationButtonUseCases(
            addMeditationButtonUseCase = AddMeditationButtonUseCase(meditationButtonRepositoryImpl),
            deleteMeditationButtonUseCase = DeleteMeditationButtonUseCase(
                meditationButtonRepositoryImpl
            ),
            getMeditationButtonByIdUseCase = GetMeditationButtonByIdUseCase(
                meditationButtonRepositoryImpl
            ),
            getMeditationButtonsUseCase = GetMeditationButtonsUseCase(meditationButtonRepositoryImpl)
        )

}