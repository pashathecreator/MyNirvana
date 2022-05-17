package com.example.mynirvana.presentation.mainFragments.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.readyMeditationsData.ReadyMeditations
import com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.pomodoro.readyPomodorosData.ReadyPomodoros
import com.example.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
import com.example.mynirvana.domain.sharedPreferences.usecases.SharedPreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor
    (
    private val meditationUseCases: MeditationUseCases,
    private val pomodoroUseCases: PomodoroUseCases,
    private val sharedPreferencesUseCases: SharedPreferencesUseCases
) : ViewModel() {

    private val meditationMutableLiveData = MutableLiveData<List<Meditation>>()
    val meditationLiveData: LiveData<List<Meditation>>
        get() = meditationMutableLiveData

    private val pomodorosMutableLiveData = MutableLiveData<List<Pomodoro>>()
    val pomodorosLiveData: LiveData<List<Pomodoro>>
        get() = pomodorosMutableLiveData

    private val userNameMutableLiveData = MutableLiveData<String>()
    val userNameLiveData: LiveData<String>
        get() = userNameMutableLiveData

    init {
        getUserNameFromSharedPreferences()
        getUserMeditationsFromDataBase()
        getUserPomodorosFromDatabase()
    }

    fun getReadyMeditations(): List<Meditation> {
        val readyMeditations = mutableListOf<Meditation>()
        ReadyMeditations.values().forEach {
            val header = it.meditation.name
            val imageResourceId = it.meditation.imageResourceId
            val time = it.meditation.time
            val backgroundResourceId = it.meditation.backgroundSoundResourceId
            val endSoundResourceId = it.meditation.endSoundResourceId
            val isMeditationCanBeDeleted = it.meditation.isMeditationCanBeDeleted
            readyMeditations.add(
                Meditation(
                    name = header,
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


    private fun getUserPomodorosFromDatabase() {
        viewModelScope.launch {
            pomodoroUseCases.getPomodorosUseCase.invoke().collect {
                pomodorosMutableLiveData.postValue(it)
            }
        }
    }

    fun getUserNameFromSharedPreferences() = viewModelScope.launch {
        userNameMutableLiveData.postValue(sharedPreferencesUseCases.getUserNameUseCase.invoke())
    }

    fun getReadyPomodoros(): List<Pomodoro> {
        val readyPomodoros = mutableListOf<Pomodoro>()

        ReadyPomodoros.values().forEach {
            readyPomodoros.add(
                Pomodoro(
                    name = it.pomodoro.name,
                    workTimeInSeconds = it.pomodoro.workTimeInSeconds,
                    relaxTimeInSeconds = it.pomodoro.relaxTimeInSeconds,
                    quantityOfCircles = it.pomodoro.quantityOfCircles,
                    imageResourceId = it.pomodoro.imageResourceId
                )
            )
        }

        return readyPomodoros
    }

    fun deletePomodoro(pomodoro: Pomodoro) {
        viewModelScope.launch {
            pomodoroUseCases.deletePomodoroUseCase.invoke(pomodoro)
        }
    }
}