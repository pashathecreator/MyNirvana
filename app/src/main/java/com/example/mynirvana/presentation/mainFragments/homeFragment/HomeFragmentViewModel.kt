package com.example.mynirvana.presentation.mainFragments.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.readyMeditationsData.ReadyMeditations
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
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

    private val meditationMutableLiveData = MutableLiveData<List<Meditation>>()
    val meditationLiveData: LiveData<List<Meditation>>
        get() = meditationMutableLiveData

    init {
        getUserMeditationsFromDataBase()
    }

    fun getReadyMeditations(): List<Meditation> {
        val readyMeditations = mutableListOf<Meditation>()
        ReadyMeditations.values().forEach {
            val header = it.meditation.header
            val imageResourceId = it.meditation.imageResourceId
            val time = it.meditation.time
            val backgroundResourceId = it.meditation.backgroundSoundResourceId
            val endSoundResourceId = it.meditation.endSoundResourceId
            val isMeditationCanBeDeleted = it.meditation.isMeditationCanBeDeleted
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
                meditationMutableLiveData.postValue(it)
            }
        }
    }
}