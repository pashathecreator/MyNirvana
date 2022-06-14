package com.skelrath.mynirvana.domain.meditations.model.meditation

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skelrath.mynirvana.domain.notification.RandomIdCreator
import java.io.Serializable


@Entity
data class Meditation(
    val name: String? = "",
    val time: Long? = 600,
    val imageResourceId: Int? = 0,
    val backgroundSoundResourceId: Int? = 0,
    val endSoundResourceId: Int? = 0,
    val isMeditationCanBeDeleted: Boolean? = true,
    var isMeditationCompleted: Boolean? = false,
    val isMeditationCanBeRestarted: Boolean? = true,
    val fireBaseId: Int? = RandomIdCreator.createId(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable