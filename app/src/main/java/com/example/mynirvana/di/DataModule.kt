package com.example.mynirvana.di

import com.example.mynirvana.data.meditation.data_source.MeditationButtonDao
import com.example.mynirvana.data.meditation.repository.MeditationButtonRepositoryImpl
import com.example.mynirvana.domain.meditationbuttons.repository.MeditationButtonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMeditationButtonRepository(dao: MeditationButtonDao): MeditationButtonRepository =
        MeditationButtonRepositoryImpl(dao = dao)

}