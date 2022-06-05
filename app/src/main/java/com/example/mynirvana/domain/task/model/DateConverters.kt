package com.example.mynirvana.domain.task.model

import androidx.room.TypeConverter
import java.sql.Date

class DateConverters {
    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(time: Long?): Date? = time?.let { Date(it) }
}