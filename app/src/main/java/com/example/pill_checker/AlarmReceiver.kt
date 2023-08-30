package com.example.pill_checker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.edit


class MorningAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val sharedPreferences = context?.getSharedPreferences("EventLock", Context.MODE_PRIVATE)
        val morningLock = sharedPreferences?.getBoolean("morning", false)
        if (morningLock == true) return
        //Event 중복방지
        sharedPreferences?.edit {
            putBoolean("sleep", false)
            putBoolean("morning", true)
        }
    }
}

class LunchAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val sharedPreferences = context?.getSharedPreferences("EventLock", Context.MODE_PRIVATE)
        val lunchLock = sharedPreferences?.getBoolean("lunch", false)
        if (lunchLock == true) return
        //Event 중복방지
        sharedPreferences?.edit {
            putBoolean("morning", false)
            putBoolean("lunch", true)
        }
    }
}

class DinnerAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val sharedPreferences = context?.getSharedPreferences("EventLock", Context.MODE_PRIVATE)
        val dinnerLock = sharedPreferences?.getBoolean("dinner", false)
        if (dinnerLock == true) return
        //Event 중복방지
        sharedPreferences?.edit {
            putBoolean("lunch", false)
            putBoolean("dinner", true)
        }
    }
}

class SleepAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val sharedPreferences = context?.getSharedPreferences("EventLock", Context.MODE_PRIVATE)
        val sleepLock = sharedPreferences?.getBoolean("sleep", false)
        if (sleepLock == true) return
        //Event 중복방지
        sharedPreferences?.edit {
            putBoolean("dinner", false)
            putBoolean("sleep", true)
        }
    }
}