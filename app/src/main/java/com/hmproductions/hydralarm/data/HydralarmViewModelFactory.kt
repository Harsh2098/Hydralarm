package com.hmproductions.hydralarm.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HydralarmViewModelFactory(private val preferences: DataStore<Preferences>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HydralarmViewModel::class.java)) {
            return HydralarmViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}