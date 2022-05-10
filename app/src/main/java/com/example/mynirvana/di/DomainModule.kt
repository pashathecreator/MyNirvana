package com.example.mynirvana.di

import android.content.Context
import com.example.mynirvana.data.task.repository.TaskRepositoryImpl
import com.example.mynirvana.data.habit.repository.HabitRepositoryImpl
import com.example.mynirvana.data.meditations.meditation.repository.MeditationRepositoryImpl
import com.example.mynirvana.data.meditations.meditationCourses.repository.MeditationCoursesRepositoryImpl
import com.example.mynirvana.data.pomodoro.repository.PomodoroRepositoryImpl
import com.example.mynirvana.data.sharedPrefernecs.repository.SharedPreferencesRepositoryImplementation
import com.example.mynirvana.domain.task.useCases.AddTaskUseCase
import com.example.mynirvana.domain.task.useCases.TaskUseCases
import com.example.mynirvana.domain.task.useCases.DeleteTaskUseCase
import com.example.mynirvana.domain.task.useCases.GetTasksByDateUseCase
import com.example.mynirvana.domain.habit.useCases.AddHabitUseCase
import com.example.mynirvana.domain.habit.useCases.DeleteHabitUseCase
import com.example.mynirvana.domain.habit.useCases.GetHabitsUseCase
import com.example.mynirvana.domain.habit.useCases.HabitUseCases
import com.example.mynirvana.domain.mediaPlayer.MusicPlayer
import com.example.mynirvana.domain.mediaPlayer.MusicPlayerService
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.CreateMeditationCoursesUseCase
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.GetMeditationCoursesUseCase
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.InsertMeditationListUseCase
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.GetMeditationsUseCase
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.AddMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.DeleteMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.GetMeditationByIdUseCase
import com.example.mynirvana.domain.pomodoro.useCases.AddPomodoroUseCase
import com.example.mynirvana.domain.pomodoro.useCases.DeletePomodoroUseCase
import com.example.mynirvana.domain.pomodoro.useCases.GetPomodorosUseCase
import com.example.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
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
    fun providesPomodoroUseCases(pomodoroRepositoryImpl: PomodoroRepositoryImpl): PomodoroUseCases =
        PomodoroUseCases(
            addPomodoroUseCase = AddPomodoroUseCase(pomodoroRepositoryImpl),
            deletePomodoroUseCase = DeletePomodoroUseCase(pomodoroRepositoryImpl),
            getPomodorosUseCase = GetPomodorosUseCase(pomodoroRepositoryImpl)
        )

    @Provides
    fun providesTaskUseCases(taskRepositoryImpl: TaskRepositoryImpl) = TaskUseCases(
        addTaskUseCase = AddTaskUseCase(taskRepositoryImpl),
        deleteCaseUseCase = DeleteTaskUseCase(taskRepositoryImpl),
        getTasksByDateUseCase = GetTasksByDateUseCase(taskRepositoryImpl)
    )

    @Provides
    fun providesHabitUseCases(habitRepositoryImpl: HabitRepositoryImpl) =
        HabitUseCases(
            addHabitUseCase = AddHabitUseCase(habitRepositoryImpl),
            deleteHabitUseCase = DeleteHabitUseCase(habitRepositoryImpl),
            getHabitsUseCase = GetHabitsUseCase(habitRepositoryImpl)
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
    fun providesMediaPlayer(@ApplicationContext applicationContext: Context): MusicPlayer =
        MusicPlayerService(applicationContext)

}