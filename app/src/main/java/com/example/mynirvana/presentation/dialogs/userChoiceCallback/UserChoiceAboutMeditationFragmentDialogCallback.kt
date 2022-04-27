package com.example.mynirvana.presentation.dialogs.userChoiceCallback

interface UserChoiceAboutMeditationFragmentDialogCallback {
    fun sendUserChoiceFromFragmentDialog(userChoice: Boolean)
    fun userChoiceFragmentDialogDismissed(isDismissedByCrossButton: Boolean)
}