package com.example.mynirvana.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynirvana.domain.meditationButtons.model.Meditation
import com.example.mynirvana.domain.meditationButtons.readyMeditationButtonsData.ReadyMeditations
import com.example.mynirvana.domain.meditationButtons.usecases.MeditationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor
    (
    private val meditationButtonUseCases: MeditationUseCases
) : ViewModel() {


    fun getReadyMeditations(): List<Meditation> {
        val readyMeditations = mutableListOf<Meditation>()
        ReadyMeditations.values().forEach {
            val header = it.meditationButton.header
            val imageResourceId = it.meditationButton.imageResourceId
            val time = it.meditationButton.time

            readyMeditations.add(
                Meditation(
                    header = header,
                    imageResourceId = imageResourceId,
                    time = time
                )
            )

        }

        return readyMeditations

    }

    private val meditationButtonsMutableLiveData = MutableLiveData<Meditation>()
    val meditationButtonLiveData: LiveData<Meditation> = meditationButtonsMutableLiveData

}