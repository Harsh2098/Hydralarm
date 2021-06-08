package com.hmproductions.hydralarm.reminder

import android.app.IntentService
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.hmproductions.hydralarm.utils.Constants
import com.hmproductions.hydralarm.utils.Constants.NOTIFICATION_ID_KEY

class IncrementCountService : IntentService("IncrementCountService") {
    override fun onHandleIntent(intent: Intent?) {
        val notificationId = intent?.getIntExtra(NOTIFICATION_ID_KEY, -1) ?: -1

        if (notificationId != -1) {
            NotificationManagerCompat.from(applicationContext).cancel(notificationId)
        }

        val incrementRequest = OneTimeWorkRequestBuilder<IncrementCountWorker>()
            .addTag(Constants.INCREMENT_WORKER_TAG)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(incrementRequest)
    }
}