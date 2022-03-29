package com.example.mynirvana.domain.meditations.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Meditation(
    val header: String,
    val time: Long,
    val imageResourceId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)