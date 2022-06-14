package com.skelrath.mynirvana.domain.meditations.model.meditation

import android.content.Context
import com.skelrath.mynirvana.domain.notification.RandomIdCreator

data class MeditationForRealTimeDatabase(
    val name: String? = "",
    val time: Long? = 600,
    val imageResourceId: String? = "com.skelrath.mynirvana:drawable/ic_rectangle_blue",
    val backgroundSoundResourceId: String? = "com.skelrath.mynirvana:raw/fire_sound",
    val endSoundResourceId: String? = "com.skelrath.mynirvana:raw/guitar_sound",
    val fireBaseId: Int? = null
) {
    companion object {
        fun convertMeditationForRealTimeDatabaseIntoMeditation(
            meditationForRealTimeDatabase: MeditationForRealTimeDatabase,
            context: Context
        ): Meditation = Meditation(
            name = meditationForRealTimeDatabase.name,
            time = meditationForRealTimeDatabase.time,
            imageResourceId = context.resources.getIdentifier(
                meditationForRealTimeDatabase.imageResourceId,
                "id",
                context.packageName
            ),
            backgroundSoundResourceId = context.resources.getIdentifier(
                meditationForRealTimeDatabase.backgroundSoundResourceId,
                "id",
                context.packageName
            ),
            endSoundResourceId = context.resources.getIdentifier(
                meditationForRealTimeDatabase.endSoundResourceId,
                "id",
                context.packageName
            ),
            isMeditationCanBeRestarted = true,
            isMeditationCompleted = false,
            isMeditationCanBeDeleted = true,
            fireBaseId = meditationForRealTimeDatabase.fireBaseId
        )

        fun convertMeditationIntoMeditationForRealTimeDatabase(
            meditation: Meditation,
            context: Context
        ) =
            MeditationForRealTimeDatabase(
                name = meditation.name,
                time = meditation.time,
                imageResourceId = context.resources.getResourceName(meditation.imageResourceId!!),
                backgroundSoundResourceId = context.resources.getResourceName(meditation.backgroundSoundResourceId!!),
                endSoundResourceId = context.resources.getResourceName(meditation.endSoundResourceId!!),
                fireBaseId = meditation.fireBaseId
            )
    }
}