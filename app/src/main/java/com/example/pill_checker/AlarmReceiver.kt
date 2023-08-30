package com.example.pill_checker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.edit
import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.repo.PillCheckRepo
import com.example.pill_checker.repo.TimeRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MorningAlarmReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        val sharedPreferences = context?.getSharedPreferences("EventLock", Context.MODE_PRIVATE)
        val morningLock = sharedPreferences?.getBoolean("morning", false)
        if (morningLock == true) return
        //Event 중복방지
        sharedPreferences?.edit {
            putBoolean("sleep", false)
            putBoolean("morning", true)
        }
        if(context != null){
            val db = MainDatabase.getDatabase(context.applicationContext)
            val pillCheckRepo = PillCheckRepo(db)
            val timeRepo = TimeRepo(db)
            CoroutineScope(Dispatchers.IO).launch {
                val tidNow = DateTimeManager.getDateTimeValueNow().and(0b0100).toInt()
                val pillLightsNext = pillCheckRepo.getPillLightsByTid(tidNow)
                if(pillLightsNext.isNotEmpty()){
                    val consideredTid = timeRepo.pastLastTid(tidNow)
                    if(consideredTid != null){
                        val pillLights = pillCheckRepo.getPillLightsByTid(consideredTid)
                        pillCheckRepo.pillLightToPillChecked(pillLights)
                    }
                    //TODO push notification
                }
            }
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