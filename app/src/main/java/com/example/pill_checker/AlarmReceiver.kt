package com.example.pill_checker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    //CALLED AFTER THE TIME IS
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)

        // Calculate the time for the new alarm
        val time = getTimeValue()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)

        val currentTimeMillis = System.currentTimeMillis()
        val newAlarmTimeMillis = currentTimeMillis + 60 * 1000 // 60 seconds

        // Set the new alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, newAlarmTimeMillis, pendingIntent)
    }
}