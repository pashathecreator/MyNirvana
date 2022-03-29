package com.example.mynirvana.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.domain.meditations.readyMeditationsData.ReadyMeditations
import com.example.mynirvana.domain.meditations.usecases.MeditationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor
    (
    private val meditationUseCases: MeditationUseCases
) : ViewModel() {

    init {
        getUserMeditationFromDataBase()
    }

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

    private val meditationButtonsMutableLiveData = MutableLiveData<List<Meditation>>()
    val meditationButtonLiveData: LiveData<List<Meditation>> = meditationButtonsMutableLiveData


    fun getUserMeditationFromDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            meditationButtonsMutableLiveData.postValue(meditationUseCases.getMeditationsUseCase.invoke())
        }
    }
}