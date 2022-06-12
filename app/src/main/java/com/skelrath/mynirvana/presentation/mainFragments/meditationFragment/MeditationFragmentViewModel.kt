package com.skelrath.mynirvana.presentation.mainFragments.meditationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.skelrath.mynirvana.domain.meditations.readyMeditationsData.ReadyMeditations
import com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationFragmentViewModel @Inject constructor(
    private val meditationUseCases: MeditationUseCases,
    private val meditationCoursesUseCases:
    MeditationCoursesUseCases
) :
    ViewModel() {


    private val meditationMutableLiveData = MutableLiveData<List<Meditation>>()
    val meditationLiveData: LiveData<List<Meditation>>
        get() = meditationMutableLiveData

    private val meditationCoursesMutableLiveData = MutableLiveData<List<MeditationCourse>>()
    val meditationCourseLiveData: MutableLiveData<List<MeditationCourse>>
        get() =
            meditationCoursesMutableLiveData

    init {
        getUserMeditationsFromDataBase()
        getMeditationCourses()
    }

    fun getReadyMeditations(): List<Meditation> {
        val readyMeditations = mutableListOf<Meditation>()

        ReadyMeditations.values().forEach {
            readyMeditations.add(
                Meditation(
                    name = it.meditation.name,
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

    private fun getMeditationCourses() {
        viewModelScope.launch {
            meditationCoursesUseCases.getMeditationCoursesUseCase.invoke().collect {
                meditationCoursesMutableLiveData.postValue(it)
            }
        }
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