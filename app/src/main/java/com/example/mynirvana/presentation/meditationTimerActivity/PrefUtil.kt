package com.example.mynirvana.presentation.meditationTimerActivity

import android.content.Context
import androidx.preference.PreferenceManager

object PrefUtil {

    fun getTimerLength(context: Context): Int {
        return 1
    }

    private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.example.timer.previous_timer_length"

    fun getPreviousTimerLengthSeconds(context: Context): Long {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
    }

    fun setPreviousTimerLengthSeconds(seconds: Long, context: Context) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)

        editor.apply()
    }

    private const val TIMER_STATE_ID = "com.example.timer.timer_state"

    fun getTimerState(context: Context): MeditationTimerActivity.TimerState {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val ordinal = preferences.getInt(TIMER_STATE_ID, 0)

        return MeditationTimerActivity.TimerState.values()[ordinal]
    }

    fun setTimerState(state: MeditationTimerActivity.TimerState, context: Context) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        val ordinal = state.ordinal
        editor.putInt(TIMER_STATE_ID, ordinal)

        editor.apply()
    }

    private const val SECONDS_REMAINING_ID = "com.example.timer.seconds_remaining"

    fun getSecondsRemaining(context: Context): Long {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        return preferences.getLong(SECONDS_REMAINING_ID, 0)
    }

    fun setSecondsRemaining(seconds: Long, context: Context) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putLong(SECONDS_REMAINING_ID, seconds)

        editor.apply()
    }
}