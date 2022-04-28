package com.example.mynirvana.domain.meditations.model

data class MeditationCourse(
    val name: String,
    val meditationList: List<Meditation>,
    var completedMeditations: List<Meditation>
)