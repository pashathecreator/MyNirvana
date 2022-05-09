package com.example.mynirvana.data.meditations.meditationCourses.repository

import com.example.mynirvana.data.meditations.meditationCourses.dataSource.MeditationCourseDao
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.domain.meditations.repository.MeditationCoursesRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class MeditationCoursesRepositoryImpl @Inject constructor(private val dao: MeditationCourseDao) :
    MeditationCoursesRepository {
    override suspend fun getMeditationCourses(): Flow<List<MeditationCourse>> {
        return dao.getMeditationCourses()
    }

    override suspend fun getMeditationCourseById(id: Int): MeditationCourse? {
        return dao.getMeditationCourseById(id)
    }

    override suspend fun insertMeditationList(meditations: List<Meditation>, id: Int) {
        return dao.insertMeditationList(meditations, id)
    }

    override suspend fun insertMeditationCourse(meditationCourse: MeditationCourse) {
        dao.insertMeditationCourse(meditationCourse)
    }

    override suspend fun deleteMeditationCourse(meditationCourse: MeditationCourse) {
        dao.deleteMeditationCourse(meditationCourse)
    }


}