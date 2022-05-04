package com.example.mynirvana.data.pomodoro.repository

import com.example.mynirvana.data.pomodoro.dataSource.PomodoroDao
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.pomodoro.repository.PomodoroRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class PomodoroRepositoryImpl @Inject constructor(private val dao: PomodoroDao) :
    PomodoroRepository {
    override suspend fun getPomodoroes(): Flow<List<Pomodoro>> = dao.getPomidoros()

    override suspend fun getPomodoroById(id: Int): Pomodoro? = dao.getPomodoroById(id)

    override suspend fun insertPomodoro(pomodoro: Pomodoro) = dao.insertPomodoro(pomodoro)

    override suspend fun deleteMeditation(pomodoro: Pomodoro) = dao.deletePomodoro(pomodoro)

}