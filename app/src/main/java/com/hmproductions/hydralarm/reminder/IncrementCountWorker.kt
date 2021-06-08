package com.hmproductions.hydralarm.reminder

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.hmproductions.hydralarm.data.PreferencesKeys.GLASS_COUNT_KEY
import com.hmproductions.hydralarm.data.dataStore
import com.hmproductions.hydralarm.utils.Constants.INCREMENT_DONE_FLAG
import kotlinx.coroutines.flow.first

class IncrementCountWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val dataStore = applicationContext.dataStore

        val preferences = dataStore.data.first()
        val currentCount = preferences[GLASS_COUNT_KEY] ?: 0

        dataStore.edit {
            it[GLASS_COUNT_KEY] = currentCount + 1
        }

        return Result.success()
    }
}