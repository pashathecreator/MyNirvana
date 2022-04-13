package com.example.mynirvana.di

import android.content.Context
import com.example.mynirvana.data.meditation.repository.MeditationButtonRepositoryImpl
import com.example.mynirvana.domain.meditationMusic.MusicPlayer
import com.example.mynirvana.domain.meditationMusic.MusicPlayerService
import com.example.mynirvana.domain.meditations.usecases.GetMeditationsUseCase
import com.example.mynirvana.domain.meditations.usecases.MeditationUseCases
import com.example.mynirvana.domain.meditations.usecases.AddMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.DeleteMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.GetMeditationByIdUseCase
import com.example.mynirvana.domain.timer.Timer
import com.example.mynirvana.domain.timer.TimerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext


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


    @Provides
    fun providesTimer(): Timer = TimerService()

    @Provides
    fun providesMediaPlayer(): MusicPlayer = MusicPlayerService()


}