package com.example.mynirvana.data.meditation.repository

import androidx.lifecycle.LiveData
import com.example.mynirvana.data.meditation.data_source.MeditationButtonDao
import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton
import com.example.mynirvana.domain.meditationbuttons.repository.MeditationButtonRepository

class MeditationButtonRepositoryImpl
    (private val dao: MeditationButtonDao) : MeditationButtonRepository {

    override fun getMeditationButtons(): LiveData<List<MeditationButton>> {
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