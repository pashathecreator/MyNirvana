package com.example.mynirvana.data.meditation.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton
import kotlinx.coroutines.flow.Flow


@Dao
interface MeditationButtonDao {

    @Query("SELECT * FROM meditation_button")
    fun getMeditationButtons(): List<MeditationButton>


    @Query("SELECT * FROM meditation_button WHERE id = :id")
    suspend fun getMeditationButtonById(id: Int): MeditationButton?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeditationButton(meditationButton: MeditationButton)

    @Delete
    suspend fun deleteMeditationButton(meditationButton: MeditationButton)


}