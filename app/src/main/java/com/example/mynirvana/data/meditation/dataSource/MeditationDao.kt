package com.example.mynirvana.data.meditation.dataSource

import androidx.room.*
import com.example.mynirvana.domain.meditations.model.Meditation
import kotlinx.coroutines.flow.Flow


@Dao
interface MeditationButtonDao {

    @Query("SELECT * FROM meditation")
    fun getMeditations(): Flow<List<Meditation>>


    @Query("SELECT * FROM meditation WHERE id = :id")
    suspend fun getMeditationById(id: Int): Meditation?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeditation(meditationButton: Meditation)

    @Delete
    suspend fun deleteMeditation(meditationButton: Meditation)


}