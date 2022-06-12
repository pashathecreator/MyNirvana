package com.skelrath.mynirvana.domain.meditations.model.meditationCourse.typeConverter

import androidx.room.TypeConverter
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MeditationConverters {

    @TypeConverter
    fun fromMeditations(meditationNamesList: List<Meditation>): String {
        val gson = Gson()
        return gson.toJson(meditationNamesList)
    }

    @TypeConverter
    fun toMeditations(data: String): List<Meditation> {
        val gson = Gson()
        val listType = object : TypeToken<List<Meditation>>() {}.type
        return gson.fromJson(data, listType)
    }
}