package com.example.mynirvana.presentation.activities.meditationCreatorActivity

interface SaveMeditationAndStartCallback {
    fun saveMeditationAndStartFragmentDialogAskForStartMeditation(isDialogAskingForStartMeditation: Boolean)
    fun saveMeditationAndStartFragmentDialogDismissed(isDismissedByCrossButton: Boolean)
}