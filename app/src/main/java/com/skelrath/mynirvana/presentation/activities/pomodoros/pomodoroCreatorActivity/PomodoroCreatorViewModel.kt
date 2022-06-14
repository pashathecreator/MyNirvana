package com.skelrath.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.skelrath.mynirvana.domain.notification.RandomIdCreator
import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import com.skelrath.mynirvana.domain.pomodoro.model.PomodoroForRealTimeDatabase
import com.skelrath.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
import com.skelrath.mynirvana.presentation.fireBaseConstants.FireBaseConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PomodoroCreatorViewModel @Inject constructor(
    private val applicationContext: Context,
    private val pomodoroUseCases: PomodoroUseCases
) :
    ViewModel() {

    fun savePomodoroTimer(pomodoro: Pomodoro) {
        viewModelScope.launch {
            pomodoroUseCases.addPomodoroUseCase.invoke(pomodoro)
            savePomodoroToRealTimeDatabase(pomodoro)
        }
    }

    private val currentUser = FirebaseAuth.getInstance().currentUser!!
    private val databaseReference = FirebaseDatabase.getInstance().reference

    private fun savePomodoroToRealTimeDatabase(pomodoro: Pomodoro) {
        databaseReference.child(FireBaseConstants.USERS).child(currentUser.uid)
            .child(FireBaseConstants.POMODOROS).child(pomodoro.fireBaseId.toString())
            .setValue(
                PomodoroForRealTimeDatabase.convertPomodoroIntoPomodoroForRealTimeDatabase(
                    pomodoro,
                    applicationContext
                )
            )
    }
}