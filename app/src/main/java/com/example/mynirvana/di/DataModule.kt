package com.example.mynirvana.di

import android.content.Context
import androidx.room.Room
import com.example.mynirvana.data.meditation.dataSource.MeditationDatabase
import com.example.mynirvana.data.meditationCourses.dataSource.MeditationCourseDatabase
import com.example.mynirvana.data.pomodoro.dataSource.PomodoroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun providesMeditationDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MeditationDatabase::class.java,
        MeditationDatabase.DATABASE_NAME
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun providesMeditationDao(db: MeditationDatabase) = db.getMeditationDao()

    @Singleton
    @Provides
    fun providesMeditationCourseDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            MeditationCourseDatabase::class.java,
            MeditationCourseDatabase.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun providesMeditationCourseDao(db: MeditationCourseDatabase) = db.getMeditationCourseDao()


    @Singleton
    @Provides
    fun providesPomodoroDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        PomodoroDatabase::class.java,
        PomodoroDatabase.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun providesPomodoroDao(db: PomodoroDatabase) = db.getPomodoroDao()


}