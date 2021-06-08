package com.hmproductions.hydralarm.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HydralarmViewModel : ViewModel() {

    private val _intervals = MutableLiveData<List<Interval>>(listOf())
    val intervals: LiveData<List<Interval>> = _intervals

    init {
        _intervals.value = listOf(Interval(15, false), Interval(30, false), Interval(60, false))
    }

    fun onIntervalClick(minute: Int) {
        val updatedIntervals = mutableListOf<Interval>()
        intervals.value?.forEach {
            updatedIntervals.add(Interval(it.minute, it.minute == minute))
        }
        _intervals.value = updatedIntervals
    }
}