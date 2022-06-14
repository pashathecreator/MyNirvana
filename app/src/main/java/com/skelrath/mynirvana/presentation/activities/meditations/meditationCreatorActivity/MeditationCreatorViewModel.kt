package com.skelrath.mynirvana.presentation.activities.meditations.meditationCreatorActivity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.model.meditation.MeditationForRealTimeDatabase
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
import com.skelrath.mynirvana.domain.notification.RandomIdCreator
import com.skelrath.mynirvana.presentation.fireBaseConstants.FireBaseConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationCreatorViewModel @Inject constructor
    (
    private val applicationContext: Context,
    private val meditationButtonUseCases: MeditationUseCases
) :
    ViewModel() {

    fun saveMeditation(meditation: Meditation) {
        viewModelScope.launch {
            meditationButtonUseCases.addMeditationUseCase.invoke(meditation)
            saveMeditationToRealTimeDatabase(meditation)
        }
    }

    private val currentUser = FirebaseAuth.getInstance().currentUser!!
    private val databaseReference = FirebaseDatabase.getInstance().reference

    private fun saveMeditationToRealTimeDatabase(meditation: Meditation) {
        databaseReference.child(FireBaseConstants.USERS).child(currentUser.uid)
            .child(FireBaseConstants.MEDITATIONS).child(meditation.fireBaseId.toString())
            .setValue(
                MeditationForRealTimeDatabase.convertMeditationIntoMeditationForRealTimeDatabase(
                    meditation, applicationContext
                )
            )
    }

}