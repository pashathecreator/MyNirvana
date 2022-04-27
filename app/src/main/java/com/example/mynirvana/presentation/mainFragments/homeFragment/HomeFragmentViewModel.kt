package com.example.mynirvana.presentation.mainFragments.homeFragment

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
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor
    (
    private val meditationUseCases: MeditationUseCases
) : ViewModel() {

    private val meditationButtonsMutableLiveData = MutableLiveData<List<Meditation>>()
    val meditationButtonLiveData: LiveData<List<Meditation>> = meditationButtonsMutableLiveData

    init {
        getUserMeditationsFromDataBase()
    }

    fun getReadyMeditations(): List<Meditation> {
        val readyMeditations = mutableListOf<Meditation>()
        ReadyMeditations.values().forEach {
            val header = it.meditationButton.header
            val imageResourceId = it.meditationButton.imageResourceId
            val time = it.meditationButton.time
            val backgroundResourceId = it.meditationButton.backgroundSoundResourceId
            val endSoundResourceId = it.meditationButton.endSoundResourceId
            val isMeditationCanBeDeleted = it.meditationButton.isMeditationCanBeDeleted
            readyMeditations.add(
                Meditation(
                    header = header,
                    imageResourceId = imageResourceId,
                    backgroundSoundResourceId = backgroundResourceId,
                    endSoundResourceId = endSoundResourceId,
                    time = time,
                    isMeditationCanBeDeleted = isMeditationCanBeDeleted

                )
            )

        }

        return readyMeditations

    }

    fun deleteMeditationFromDataBase(meditation: Meditation) {
        viewModelScope.launch {
            meditationUseCases.deleteMeditationUseCase.invoke(meditation)
        }
    }


    private fun getUserMeditationsFromDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            meditationUseCases.getMeditationsUseCase.invoke().collect {
                meditationButtonsMutableLiveData.postValue(it)
            }
        }
    }
}