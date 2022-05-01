package com.example.mynirvana.domain.meditations.model.meditationCourse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynirvana.data.meditationCourses.dataSource.MeditationCourseDatabase
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import java.io.Serializable

@Entity(tableName = MeditationCourseDatabase.DATABASE_NAME)
data class MeditationCourse(
    val name: String,
    var meditationList: List<Meditation>,
    val imageResourceId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable