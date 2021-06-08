package com.hmproductions.hydralarm.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmproductions.hydralarm.data.PreferencesKeys.INTERVAL_MINUTE_KEY
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HydralarmViewModel(private val preferences: DataStore<Preferences>) : ViewModel() {

    private val _intervals = MutableLiveData<List<Interval>>(listOf())
    val intervals: LiveData<List<Interval>> = _intervals

    private val defaultMinutes = listOf(15, 30, 60)

    init {
        initialiseIntervals()
    }

    private fun initialiseIntervals() = viewModelScope.launch {
        val preferences = preferences.data.first()
        val userMinuteInterval = preferences[INTERVAL_MINUTE_KEY] ?: 0
        onIntervalClick(userMinuteInterval, false)
    }

    private fun updateUserPreference(minute: Int) = viewModelScope.launch {
        preferences.edit { preferences ->
            preferences[INTERVAL_MINUTE_KEY] = minute
        }
    }

    fun onIntervalClick(minute: Int, userClicked: Boolean = true) {
        val updatedIntervals = mutableListOf<Interval>()
        updateUserPreference(minute)

        defaultMinutes.forEach {
            updatedIntervals.add(Interval(it, it == minute))
        }
        _intervals.value = updatedIntervals
    }
}