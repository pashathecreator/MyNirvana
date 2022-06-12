package com.skelrath.mynirvana.presentation.activities.meditations.meditationCreatorActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationCreatorViewModel @Inject constructor
    (private val meditationButtonUseCases: MeditationUseCases) :
    ViewModel() {

    fun saveMeditation(meditation: Meditation) {
        viewModelScope.launch {
            meditationButtonUseCases.addMeditationUseCase.invoke(meditation)
        }
    }

}