package com.example.mynirvana.presentation.activities.meditations.meditationCreatorActivity

interface SaveMeditationAndStartCallback {
    fun saveMeditationAndStartFragmentDialogAskForStartMeditation(isDialogAskingForStartMeditation: Boolean)
    fun saveMeditationAndStartFragmentDialogDismissed(isDismissedByCrossButton: Boolean)
}