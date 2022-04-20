package com.example.mynirvana.presentation.userChoiceCallback

interface UserChoiceAboutMeditationFragmentDialogCallback {
    fun sendUserChoiceFromFragmentDialog(userChoice: Boolean)
    fun userChoiceFragmentDialogDismissed(isDismissedByCrossButton: Boolean)
}