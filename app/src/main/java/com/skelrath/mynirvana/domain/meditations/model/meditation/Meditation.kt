package com.skelrath.mynirvana.domain.meditations.model.meditation

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Meditation(
    val name: String,
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