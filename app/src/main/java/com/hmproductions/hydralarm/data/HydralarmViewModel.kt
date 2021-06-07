package com.hmproductions.hydralarm.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HydralarmViewModel : ViewModel() {

    private val _intervals = MutableLiveData<List<Interval>>(listOf())
    val intervals: LiveData<List<Interval>> = _intervals

    private val defaultIntervals =
        listOf(Interval(15, false), Interval(30, false), Interval(60, false))

    init {
        _intervals.value = defaultIntervals
    }

    fun onIntervalClick(minute: Int) {
        val updatedIntervals = defaultIntervals
        updatedIntervals.forEach { it.selected = it.minute == minute }
        _intervals.value = updatedIntervals
    }
}