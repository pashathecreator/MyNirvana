package com.example.mynirvana.domain.meditations.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynirvana.R
import java.io.Serializable


@Entity
data class Meditation(
    val header: String,
    val time: Long,
    val imageResourceId: Int,
    val soundResourceId: Int = R.raw.rain_sound,
    var isMeditationOnDelete: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable