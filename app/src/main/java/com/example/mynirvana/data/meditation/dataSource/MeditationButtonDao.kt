package com.example.mynirvana.data.meditation.dataSource

import androidx.room.*
import com.example.mynirvana.domain.meditationButtons.model.MeditationButton


@Dao
interface MeditationButtonDao {

    @Query("SELECT * FROM meditationbutton")
    fun getMeditationButtons(): List<MeditationButton>


    @Query("SELECT * FROM meditationbutton WHERE id = :id")
    suspend fun getMeditationButtonById(id: Int): MeditationButton?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeditationButton(meditationButton: MeditationButton)

    @Delete
    suspend fun deleteMeditationButton(meditationButton: MeditationButton)


}