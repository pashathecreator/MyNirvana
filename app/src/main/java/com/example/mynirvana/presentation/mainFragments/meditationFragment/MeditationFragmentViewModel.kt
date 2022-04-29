package com.example.mynirvana.presentation.mainFragments.meditationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.domain.meditations.model.MeditationCourse
import com.example.mynirvana.domain.meditations.readyMeditationsData.ReadyMeditationCourses
import com.example.mynirvana.domain.meditations.readyMeditationsData.ReadyMeditations
import com.example.mynirvana.domain.meditations.usecases.MeditationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationFragmentViewModel @Inject constructor(private val meditationUseCases: MeditationUseCases) :
    ViewModel() {

    private val meditationButtonsMutableLiveData = MutableLiveData<List<Meditation>>()
    val meditationButtonLiveData: LiveData<List<Meditation>> = meditationButtonsMutableLiveData

    init {
        getUserMeditationsFromDataBase()
    }

    fun getReadyMeditations(): List<Meditation> {
        val readyMeditations = mutableListOf<Meditation>()

        ReadyMeditations.values().forEach {
            readyMeditations.add(
                Meditation(
                    header = it.meditation.header,
                    imageResourceId = it.meditation.imageResourceId,
                    backgroundSoundResourceId = it.meditation.backgroundSoundResourceId,
                    endSoundResourceId = it.meditation.endSoundResourceId,
                    time = it.meditation.time,
                    isMeditationCanBeDeleted = it.meditation.isMeditationCanBeDeleted

                )
            )
        }

        return readyMeditations

    }

    fun getMeditationCourses(): List<MeditationCourse> {
        val readyCourses = mutableListOf<MeditationCourse>()

        ReadyMeditationCourses.values().forEach {
            readyCourses.add(
                MeditationCourse(
                    name = it.meditationCourse.name,
                    meditationList = it.meditationCourse.meditationList,
                    imageResourceId = it.meditationCourse.imageResourceId
                )
            )
        }

        return readyCourses
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