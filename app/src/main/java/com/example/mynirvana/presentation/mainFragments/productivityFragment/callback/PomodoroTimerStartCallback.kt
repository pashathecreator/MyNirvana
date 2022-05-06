package com.example.mynirvana.presentation.mainFragments.productivityFragment.callback

interface PomodoroTimerStartCallback {
    fun sendUserChoiceFromStartPomodoroDialog(userChoice: Boolean)
    fun onPomodoroStartDialogDismissed()
}