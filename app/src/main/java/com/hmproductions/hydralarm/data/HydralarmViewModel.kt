package com.hmproductions.hydralarm.data

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hmproductions.hydralarm.data.PreferencesKeys.GLASS_COUNT_KEY
import com.hmproductions.hydralarm.data.PreferencesKeys.INTERVAL_MINUTE_KEY
import com.hmproductions.hydralarm.reminder.ReminderWorker
import com.hmproductions.hydralarm.utils.Constants.DEFAULT_REMINDER_WORKER_TAG
import com.hmproductions.hydralarm.utils.Constants.INCREMENT_WORKER_TAG
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class HydralarmViewModel(private val preferences: DataStore<Preferences>, application: Application) : AndroidViewModel(application) {

    private val _intervals = MutableLiveData<List<Interval>>(listOf())
    val intervals: LiveData<List<Interval>> = _intervals

    private val _glassCount = MutableLiveData(0)
    val glassCount: LiveData<Int> = _glassCount

    private val defaultMinutes = listOf(15, 30, 60)

    init {
        initialiseIntervals()
        initialiseGlassCount()

        WorkManager.getInstance(getApplication())
            .getWorkInfosByTagLiveData(INCREMENT_WORKER_TAG)
            .observeForever {
                initialiseGlassCount()
            }
    }

    private fun initialiseIntervals() = viewModelScope.launch {
        val preferences = preferences.data.first()

        val userMinuteInterval = preferences[INTERVAL_MINUTE_KEY] ?: 0
        onIntervalClick(userMinuteInterval, false)

        _glassCount.value = preferences[GLASS_COUNT_KEY] ?: 0
    }

    private fun initialiseGlassCount() = viewModelScope.launch {
        val preferences = preferences.data.first()
        _glassCount.value = preferences[GLASS_COUNT_KEY] ?: 0
    }

    private fun updateUserPreference(minute: Int) = viewModelScope.launch {
        preferences.edit { preferences ->
            preferences[INTERVAL_MINUTE_KEY] = minute
        }
    }

    fun onIntervalClick(minute: Int, userClicked: Boolean = true) {
        val updatedIntervals = mutableListOf<Interval>()

        updateUserPreference(minute)

        if (userClicked) scheduleReminders(minute)

        defaultMinutes.forEach {
            updatedIntervals.add(Interval(it, it == minute))
        }
        _intervals.value = updatedIntervals
    }

    private fun scheduleReminders(minute: Int) {
        WorkManager.getInstance(getApplication()).cancelAllWorkByTag(DEFAULT_REMINDER_WORKER_TAG)

        val reminderRequest = PeriodicWorkRequestBuilder<ReminderWorker>(minute.toLong(), TimeUnit.MINUTES)
            .addTag(DEFAULT_REMINDER_WORKER_TAG)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(getApplication()).enqueue(reminderRequest)
    }
}