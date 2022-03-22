package com.example.mynirvana.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton
import com.example.mynirvana.domain.meditationbuttons.readyMeditationButtonsData.ReadyMeditationsDataSetCreator
import com.example.mynirvana.domain.meditationbuttons.usecases.MeditationButtonUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor
    (
    private val meditationButtonUseCases: MeditationButtonUseCases
) : ViewModel() {

    private lateinit var readyMeditations: List<MeditationButton>

    fun getReadyMeditations(): List<MeditationButton> {
        readyMeditations = ReadyMeditationsDataSetCreator.createAndReturnDataSet()

        return readyMeditations

    }

    private val meditationButtonsMutableLiveData = MutableLiveData<MeditationButton>()
    val meditationButtonLiveData: LiveData<MeditationButton> = meditationButtonsMutableLiveData

}