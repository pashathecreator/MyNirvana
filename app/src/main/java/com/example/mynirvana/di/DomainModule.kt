package com.example.mynirvana.di

import com.example.mynirvana.data.meditation.repository.MeditationButtonRepositoryImpl
import com.example.mynirvana.domain.meditations.usecases.GetMeditationsUseCase
import com.example.mynirvana.domain.meditations.usecases.MeditationUseCases
import com.example.mynirvana.domain.meditations.usecases.AddMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.DeleteMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.GetMeditationByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun providesMeditationButtonUseCases(meditationButtonRepositoryImpl: MeditationButtonRepositoryImpl): MeditationUseCases =
        MeditationUseCases(
            addMeditationUseCase = AddMeditationUseCase(meditationButtonRepositoryImpl),
            deleteMeditationUseCase = DeleteMeditationUseCase(
                meditationButtonRepositoryImpl
            ),
            getMeditationByIdUseCase = GetMeditationByIdUseCase(
                meditationButtonRepositoryImpl
            ),
            getMeditationsUseCase = GetMeditationsUseCase(meditationButtonRepositoryImpl)
        )

}