package com.example.mynirvana.domain.meditationButtons.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MeditationButton(
    val header: String,
    val time: Int,
    val imageResourceId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)