package com.example.mynirvana.di

import android.content.Context
import com.example.mynirvana.data.meditation.repository.MeditationRepositoryImpl
import com.example.mynirvana.data.meditationCourses.repository.MeditationCoursesRepositoryImpl
import com.example.mynirvana.data.sharedPrefernecs.repository.SharedPreferencesRepositoryImplementation
import com.example.mynirvana.domain.mediaPlayer.MeditationMediaPlayer
import com.example.mynirvana.domain.mediaPlayer.MeditationMediaPlayerService
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.CreateMeditationCoursesUseCase
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.GetMeditationCoursesUseCase
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.InsertMeditationListUseCase
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.GetMeditationsUseCase
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.AddMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.DeleteMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.GetMeditationByIdUseCase
import com.example.mynirvana.domain.sharedPreferences.usecases.ChangeAppRanFirstTime
import com.example.mynirvana.domain.sharedPreferences.usecases.CheckIsAppRanFirstTimeUseCase
import com.example.mynirvana.domain.sharedPreferences.usecases.SharedPreferencesUseCases
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
    fun providesMeditationButtonUseCases(meditationRepositoryImpl: MeditationRepositoryImpl): MeditationUseCases =
        MeditationUseCases(
            addMeditationUseCase = AddMeditationUseCase(meditationRepositoryImpl),
            deleteMeditationUseCase = DeleteMeditationUseCase(
                meditationRepositoryImpl
            ),
            getMeditationByIdUseCase = GetMeditationByIdUseCase(
                meditationRepositoryImpl
            ),
            getMeditationsUseCase = GetMeditationsUseCase(meditationRepositoryImpl)
        )

    @Provides
    fun providesMeditationCoursesUseCases(meditationCoursesRepositoryImpl: MeditationCoursesRepositoryImpl): MeditationCoursesUseCases =
        MeditationCoursesUseCases(
            getMeditationCoursesUseCase = GetMeditationCoursesUseCase(
                meditationCoursesRepositoryImpl
            ),
            createMeditationCoursesUseCase = CreateMeditationCoursesUseCase(
                meditationCoursesRepositoryImpl
            ),
            insertMeditationListUseCase = InsertMeditationListUseCase(
                meditationCoursesRepositoryImpl
            )
        )

    @Provides
    fun providesSharedPreferencesUseCases(sharedPreferencesRepositoryImplementation: SharedPreferencesRepositoryImplementation): SharedPreferencesUseCases =
        SharedPreferencesUseCases(
            checkIsAppRanFirstTimeUseCase = CheckIsAppRanFirstTimeUseCase(
                sharedPreferencesRepositoryImplementation
            ), changeAppRanFirstTime = ChangeAppRanFirstTime(
                sharedPreferencesRepositoryImplementation
            )
        )

    @Provides
    fun providesTimer(): Timer = TimerService()

    @Provides
    fun providesMediaPlayer(@ApplicationContext applicationContext: Context): MeditationMediaPlayer =
        MeditationMediaPlayerService(applicationContext)

}