package com.example.mynirvana.presentation.meditationCreatorActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.meditationButtons.model.Meditation
import com.example.mynirvana.domain.meditationButtons.usecases.MeditationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationCreatorViewModel @Inject constructor
    (private val meditationButtonUseCases: MeditationUseCases) :
    ViewModel() {

    fun saveMeditation(meditation: Meditation) {
        viewModelScope.launch {
            meditationButtonUseCases.addMeditationButtonUseCase.invoke(meditation)
        }
    }

}