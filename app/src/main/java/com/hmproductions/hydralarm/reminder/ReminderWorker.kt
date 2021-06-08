package com.hmproductions.hydralarm.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hmproductions.hydralarm.MainActivity
import com.hmproductions.hydralarm.R
import com.hmproductions.hydralarm.utils.Constants.NOTIFICATION_ID_KEY

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        createNotificationChannel()

        val notificationId = (System.currentTimeMillis() % 10000).toInt()

        val builder = NotificationCompat.Builder(applicationContext, DEFAULT_CHANNEL_ID)
            .setSmallIcon(R.drawable.glass_icon)
            .setContentTitle("Water Reminder")
            .setContentText("Time to gulp a glass of water!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(createLauncherIntent())
            .addAction(0, applicationContext.getString(R.string.gluped), createIncrementPendingIntent(notificationId))
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId, builder.build())
        }

        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.default_channel_name)
            val descriptionText = applicationContext.getString(R.string.default_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DEFAULT_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createLauncherIntent(): PendingIntent {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(applicationContext, 0, intent, 0)
    }

    private fun createIncrementPendingIntent(notificationId: Int): PendingIntent {
        val intent = Intent(applicationContext, IncrementCountService::class.java).apply {
            putExtra(NOTIFICATION_ID_KEY, notificationId)
        }

        return PendingIntent.getService(applicationContext, INCREMENT_SERVICE_RC, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    companion object {
        const val DEFAULT_CHANNEL_ID = "hydralarm-channel-id"
        const val INCREMENT_SERVICE_RC = 143
    }
}