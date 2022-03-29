package com.example.mynirvana.data.meditation.dataSource

import androidx.room.*
import com.example.mynirvana.domain.meditationButtons.model.Meditation


@Dao
interface MeditationButtonDao {

    @Query("SELECT * FROM meditationbutton")
    fun getMeditationButtons(): List<Meditation>


    @Query("SELECT * FROM meditationbutton WHERE id = :id")
    suspend fun getMeditationButtonById(id: Int): Meditation?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeditationButton(meditationButton: Meditation)

    @Delete
    suspend fun deleteMeditationButton(meditationButton: Meditation)


}