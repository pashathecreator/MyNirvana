package com.example.mynirvana.domain.meditations.readyMeditationsData

import com.example.mynirvana.R
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.domain.meditations.model.MeditationCourse

enum class ReadyMeditationCourses(val meditationCourse: MeditationCourse) {
    MeditationCourse1(
        MeditationCourse(
            name = "Для новичков",
            meditationList = listOf(
                Meditation(
                    header = "Первая медитация",
                    time = 300,
                    imageResourceId = R.drawable.ic_rectangle_green,
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false
                ),
                Meditation(
                    header = "Вторая медитация",
                    time = 420,
                    imageResourceId = R.drawable.ic_rectangle_green,
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false
                ),
                Meditation(
                    header = "Третья медитация",
                    time = 540,
                    imageResourceId = R.drawable.ic_rectangle_green,
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false
                ),
                Meditation(
                    header = "Четвертая медитация",
                    time = 720,
                    imageResourceId = R.drawable.ic_rectangle_green,
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false
                )
            ),
            completedMeditations = listOf()
        )
    )
}