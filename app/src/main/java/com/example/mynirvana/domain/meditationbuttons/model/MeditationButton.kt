package com.example.mynirvana.domain.meditationbuttons.model

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MeditationButton(
    val header: String,
    val time: String,
    val imageResourceId: Int,
    val isReadyMeditation: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)