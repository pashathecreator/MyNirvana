package com.example.mynirvana.data.sharedPrefernecs.repository

import android.content.Context
import com.example.mynirvana.domain.sharedPreferences.repository.SharedPreferencesRepository
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesRepositoryImplementation
@Inject constructor(@ApplicationContext private val context: Context) :
    SharedPreferencesRepository() {

    override fun getSharedPreferences(): SharedPreferences =
        context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)

    override fun <T> updateSharedPreferencesValueByKey(key: String, value: T) {
        val sharedPreferencesEditor = getSharedPreferences().edit()
        when (value) {
            is Boolean -> {
                sharedPreferencesEditor.putBoolean(key, value).apply()
            }
        }
    }

    override fun getBooleanValueByKey(key: String): Boolean =
        getSharedPreferences().getBoolean(key, true)

    private companion object {
        const val SETTINGS_NAME = "appSettings"
    }
}