package com.example.mynirvana.domain.meditations.model

import java.io.Serializable

data class MeditationCourse(
    val name: String,
    val meditationList: List<Meditation>,
    val imageResourceId: Int
) : Serializable