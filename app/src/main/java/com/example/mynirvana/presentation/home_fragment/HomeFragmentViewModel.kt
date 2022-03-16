package com.example.mynirvana.presentation.home_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton
import com.example.mynirvana.domain.meditationbuttons.ready_meditation_buttons_data.ReadyMeditationsDataSetCreator
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {

    private lateinit var readyMeditations: List<MeditationButton>

    fun getReadyMeditations(): List<MeditationButton> {
        readyMeditations = ReadyMeditationsDataSetCreator.createAndReturnDataSet()

        return readyMeditations

    }

}