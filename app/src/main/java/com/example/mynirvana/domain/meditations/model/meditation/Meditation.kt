package com.example.mynirvana.domain.meditations.model.meditation

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynirvana.R
import java.io.Serializable


@Entity
data class Meditation(
    val header: String,
    val time: Long,
    val imageResourceId: Int,
    val backgroundSoundResourceId: Int,
    val endSoundResourceId: Int,
    val isMeditationCanBeDeleted: Boolean = true,
    var isMeditationCompleted: Boolean = false,
    val isMeditationCanBeRestarted: Boolean = true,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable