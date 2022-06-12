package com.skelrath.mynirvana.domain.meditations.model.meditationCourse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skelrath.mynirvana.data.meditations.meditationCourses.dataSource.MeditationCourseDatabase
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import java.io.Serializable

@Entity(tableName = MeditationCourseDatabase.DATABASE_NAME)
data class MeditationCourse(
    val name: String,
    @ColumnInfo(name = "meditation_course_meditations")
    var meditationList: List<Meditation>,
    val imageResourceId: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "meditation_course_id")
    val id: Int? = null
) : Serializable