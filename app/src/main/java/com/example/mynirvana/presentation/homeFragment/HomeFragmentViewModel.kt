package com.example.mynirvana.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynirvana.domain.meditationButtons.model.MeditationButton
import com.example.mynirvana.domain.meditationButtons.readyMeditationButtonsData.ReadyMeditations
import com.example.mynirvana.domain.meditationButtons.usecases.MeditationButtonUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor
    (
    private val meditationButtonUseCases: MeditationButtonUseCases
) : ViewModel() {


    fun getReadyMeditations(): List<MeditationButton> {
        val readyMeditations = mutableListOf<MeditationButton>()
        ReadyMeditations.values().forEach {
            val header = it.meditationButton.header
            val imageResourceId = it.meditationButton.imageResourceId
            val time = it.meditationButton.time

            readyMeditations.add(
                MeditationButton(
                    header = header,
                    imageResourceId = imageResourceId,
                    time = time
                )
            )

        }

        return readyMeditations

    }

    private val meditationButtonsMutableLiveData = MutableLiveData<MeditationButton>()
    val meditationButtonLiveData: LiveData<MeditationButton> = meditationButtonsMutableLiveData

}