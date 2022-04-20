package com.example.mynirvana.presentation.meditationCreatorActivity

interface SaveMeditationAndStartCallback {
    fun saveMeditationAndStartFragmentDialogAskForStartMeditation(isDialogAskingForStartMeditation: Boolean)
    fun saveMeditationAndStartFragmentDialogDismissed(isDismissedByCrossButton: Boolean)
}