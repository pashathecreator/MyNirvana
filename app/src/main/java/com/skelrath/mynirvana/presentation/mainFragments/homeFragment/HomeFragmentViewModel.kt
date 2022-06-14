package com.skelrath.mynirvana.presentation.mainFragments.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.readyMeditationsData.ReadyMeditations
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import com.skelrath.mynirvana.domain.pomodoro.readyPomodorosData.ReadyPomodoros
import com.skelrath.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
import com.skelrath.mynirvana.domain.sharedPreferences.usecases.SharedPreferencesUseCases
import com.skelrath.mynirvana.presentation.fireBaseConstants.FireBaseConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private fun getUserMeditationsFromDataBase() =
        viewModelScope.launch(Dispatchers.IO) {
            meditationUseCases.getMeditationsUseCase.invoke().collect {
                meditationMutableLiveData.postValue(it)
            }
        }


    private fun getUserPomodorosFromDatabase() =
        viewModelScope.launch {
            pomodoroUseCases.getPomodorosUseCase.invoke().collect {
                pomodorosMutableLiveData.postValue(it)
            }
        }


    fun getUserNameFromSharedPreferences() {
        userNameMutableLiveData.value = sharedPreferencesUseCases.getUserNameUseCase.invoke()
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

    fun deleteMeditation(meditation: Meditation) {
        viewModelScope.launch {
            meditationUseCases.deleteMeditationUseCase.invoke(meditation)
            deleteMeditationFromRealTimeDatabase(meditation)
        }
    }

    fun deletePomodoro(pomodoro: Pomodoro) {
        viewModelScope.launch {
            pomodoroUseCases.deletePomodoroUseCase.invoke(pomodoro)
            deletePomodoroFromRealTimeDatabase(pomodoro)
        }
    }

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid

    private fun deleteMeditationFromRealTimeDatabase(meditation: Meditation) {
        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.MEDITATIONS).child(meditation.fireBaseId.toString())
            .removeValue()
    }

    private fun deletePomodoroFromRealTimeDatabase(pomodoro: Pomodoro) {
        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.POMODOROS).child(pomodoro.fireBaseId.toString())
            .removeValue()
    }
}