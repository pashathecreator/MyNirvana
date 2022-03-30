package com.example.mynirvana.data.meditation.repository


import com.example.mynirvana.data.meditation.dataSource.MeditationButtonDao
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.domain.meditations.repository.MeditationRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class MeditationButtonRepositoryImpl
@Inject constructor(private val dao: MeditationButtonDao) : MeditationRepository {

    override suspend fun getMeditations(): Flow<List<Meditation>> {
        return dao.getMeditations()
    }

    override suspend fun getMeditationById(id: Int): Meditation? {
        return dao.getMeditationById(id = id)
    }

    override suspend fun insertMeditation(meditationButton: Meditation) {
        return dao.insertMeditation(meditationButton = meditationButton)
    }

    override suspend fun deleteMeditation(meditationButton: Meditation) {
        return dao.deleteMeditation(meditationButton = meditationButton)
    }

}