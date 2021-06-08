package com.hmproductions.hydralarm.data

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object PreferencesKeys {
    const val HYDRALARM_PREFERENCES_NAME = "hydralarm_preferences"

    val INTERVAL_MINUTE_KEY = intPreferencesKey("interval-minute-key")
}

val Context.dataStore by preferencesDataStore(
    name = PreferencesKeys.HYDRALARM_PREFERENCES_NAME
)