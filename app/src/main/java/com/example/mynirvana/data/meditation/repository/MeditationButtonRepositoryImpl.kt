package com.example.mynirvana.data.meditation.repository


import com.example.mynirvana.data.meditation.dataSource.MeditationButtonDao
import com.example.mynirvana.domain.meditationButtons.model.MeditationButton
import com.example.mynirvana.domain.meditationButtons.repository.MeditationButtonRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class MeditationButtonRepositoryImpl
@Inject constructor(private val dao: MeditationButtonDao) : MeditationButtonRepository {

    override suspend fun getMeditationButtons(): List<MeditationButton> {
        return dao.getMeditationButtons()
    }

    override suspend fun getMeditationButtonById(id: Int): MeditationButton? {
        return dao.getMeditationButtonById(id = id)
    }

    override suspend fun insertMeditationButton(meditationButton: MeditationButton) {
        return dao.insertMeditationButton(meditationButton = meditationButton)
    }

    override suspend fun deleteMeditationButton(meditationButton: MeditationButton) {
        return dao.deleteMeditationButton(meditationButton = meditationButton)
    }

}