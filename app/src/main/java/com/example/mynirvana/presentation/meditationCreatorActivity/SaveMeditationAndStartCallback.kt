package com.example.mynirvana.presentation.meditationCreatorActivity

interface SaveMeditationAndStartCallback {
    fun dialogAskForStartMeditation(isDialogAskingForStartMeditation: Boolean)
    fun dialogDismiss()
}