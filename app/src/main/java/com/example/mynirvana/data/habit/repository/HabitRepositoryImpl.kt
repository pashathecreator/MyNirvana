package com.example.mynirvana.data.habit.repository

import com.example.mynirvana.data.habit.dataSource.HabitDao
import com.example.mynirvana.domain.habit.model.Habit
import com.example.mynirvana.domain.habit.repository.HabitRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class HabitRepositoryImpl @Inject constructor(private val dao: HabitDao) : HabitRepository {
    override fun getHabits(): Flow<List<Habit>> = dao.getHabits()

    override suspend fun insertHabit(habit: Habit) = dao.insertHabit(habit)

    override suspend fun deleteHabit(habit: Habit) = dao.deleteHabit(habit)
}