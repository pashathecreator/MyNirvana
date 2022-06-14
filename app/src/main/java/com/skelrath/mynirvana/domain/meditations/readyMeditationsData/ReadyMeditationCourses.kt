package com.skelrath.mynirvana.domain.meditations.readyMeditationsData

import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse

enum class ReadyMeditationCourses(val meditationCourse: MeditationCourse) {
    MeditationCourse1(
        MeditationCourse(
            name = "Для новичков",
            meditationList = listOf(
                Meditation(
                    name = "Первая медитация",
                    time = 5,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Вторая медитация",
                    time = 420,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Третья медитация",
                    time = 540,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Четвертая медитация",
                    time = 720,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                )
            ),
            imageResourceId = R.drawable.man_background
        )
    ),
    MeditationCourse2(
        MeditationCourse(
            name = "Осознанность",
            meditationList = listOf(
                Meditation(
                    name = "Первая медитация",
                    time = 420,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Вторая медитация",
                    time = 480,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Третья медитация",
                    time = 540,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Четвертая медитация",
                    time = 720,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Пятая медитация",
                    time = 720,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                )
            ),
            imageResourceId = R.drawable.flower_background
        )
    ),
    MeditationCourse3(
        MeditationCourse(
            name = "Тревожность",
            meditationList = listOf(
                Meditation(
                    name = "Первая медитация",
                    time = 600,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Вторая медитация",
                    time = 600,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Третья медитация",
                    time = 900,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Четвертая медитация",
                    time = 900,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Пятая медитация",
                    time = 900,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                )
            ),
            imageResourceId = R.drawable.fire_background

        )
    ),
    MeditationCourse4(
        MeditationCourse(
            name = "Снять стресс",
            meditationList = listOf(
                Meditation(
                    name = "Первая медитация",
                    time = 600,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Вторая медитация",
                    time = 600,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Третья медитация",
                    time = 900,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Четвертая медитация",
                    time = 900,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                ),
                Meditation(
                    name = "Пятая медитация",
                    time = 900,
                    imageResourceId = GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation(),
                    backgroundSoundResourceId = R.raw.fire_sound,
                    endSoundResourceId = R.raw.guitar_sound,
                    isMeditationCanBeDeleted = false,
                    isMeditationCompleted = false,
                    isMeditationCanBeRestarted = false
                )
            ),
            imageResourceId = R.drawable.water_background
        )
    )
}