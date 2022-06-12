package com.skelrath.mynirvana.data.meditations.meditation.dataSource

import androidx.room.*
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import kotlinx.coroutines.flow.Flow


@Dao
interface MeditationDao {
    @Query("SELECT * FROM meditation")
    fun getMeditations(): Flow<List<Meditation>>


    @Query("SELECT * FROM meditation WHERE id = :id")
    suspend fun getMeditationById(id: Int): Meditation?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeditation(meditation: Meditation)

    @Delete
    suspend fun deleteMeditation(meditation: Meditation)
}