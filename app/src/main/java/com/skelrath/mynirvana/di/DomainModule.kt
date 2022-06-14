package com.skelrath.mynirvana.di

import android.content.Context
import com.skelrath.mynirvana.data.task.repository.TaskRepositoryImpl
import com.skelrath.mynirvana.data.habit.repository.HabitRepositoryImpl
import com.skelrath.mynirvana.data.meditations.meditation.repository.MeditationRepositoryImpl
import com.skelrath.mynirvana.data.meditations.meditationCourses.repository.MeditationCoursesRepositoryImpl
import com.skelrath.mynirvana.data.pomodoro.repository.PomodoroRepositoryImpl
import com.skelrath.mynirvana.data.sharedPrefernecs.repository.SharedPreferencesRepositoryImplementation
import com.skelrath.mynirvana.domain.task.useCases.AddTaskUseCase
import com.skelrath.mynirvana.domain.task.useCases.TaskUseCases
import com.skelrath.mynirvana.domain.task.useCases.DeleteTaskUseCase
import com.skelrath.mynirvana.domain.task.useCases.GetTasksUseCase
import com.skelrath.mynirvana.domain.habit.useCases.AddHabitUseCase
import com.skelrath.mynirvana.domain.habit.useCases.DeleteHabitUseCase
import com.skelrath.mynirvana.domain.habit.useCases.GetHabitsUseCase
import com.skelrath.mynirvana.domain.habit.useCases.HabitUseCases
import com.skelrath.mynirvana.domain.mediaPlayer.MusicPlayer
import com.skelrath.mynirvana.domain.mediaPlayer.MusicPlayerService
import com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.CreateMeditationCoursesUseCase
import com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.GetMeditationCoursesUseCase
import com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.InsertMeditationListUseCase
import com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.GetMeditationsUseCase
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.AddMeditationUseCase
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.DeleteMeditationUseCase
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.GetMeditationByIdUseCase
import com.skelrath.mynirvana.domain.pomodoro.useCases.AddPomodoroUseCase
import com.skelrath.mynirvana.domain.pomodoro.useCases.DeletePomodoroUseCase
import com.skelrath.mynirvana.domain.pomodoro.useCases.GetPomodorosUseCase
import com.skelrath.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
import com.skelrath.mynirvana.domain.sharedPreferences.usecases.*
import com.skelrath.mynirvana.domain.timer.Timer
import com.skelrath.mynirvana.domain.timer.TimerService
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
        deleteTaskUseCase = DeleteTaskUseCase(taskRepositoryImpl),
        getTasksUseCase = GetTasksUseCase(taskRepositoryImpl)
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
            getUserNameUseCase = GetUserNameUseCase(sharedPreferencesRepositoryImplementation),
            changeUserNameUseCase = ChangeUserNameUseCase(sharedPreferencesRepositoryImplementation)
        )

    @Provides
    fun providesTimer(): Timer = TimerService()

    @Provides
    fun providesMediaPlayer(@ApplicationContext applicationContext: Context): MusicPlayer =
        MusicPlayerService(applicationContext)

    @Provides
    fun providesContext(@ApplicationContext applicationContext: Context) = applicationContext

}