package com.example.pill_checker

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.pill_checker.dao.MainDatabase

class MyApplication: Application(){
    val database by lazy { MainDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = R.string.channel_name.toString()
            val descriptionText = R.string.channel_description.toString()
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("PillNotice", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
