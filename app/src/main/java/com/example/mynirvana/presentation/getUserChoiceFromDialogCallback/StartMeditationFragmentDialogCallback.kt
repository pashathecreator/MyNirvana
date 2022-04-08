package com.example.mynirvana.presentation.getUserChoiceFromDialogCallback

interface StartMeditationFragmentDialogCallback {
    fun sendUserChoice(userChoice: Boolean)
    fun fragmentDismissed()
}