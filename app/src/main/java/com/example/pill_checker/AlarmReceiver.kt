package com.example.pill_checker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.edit
import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.dao.timeIter
import com.example.pill_checker.repo.PillCheckRepo
import com.example.pill_checker.repo.TimeRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MorningAlarmReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        onReceiveHandler(context, 0b0001)
    }
}

class LunchAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        onReceiveHandler(context, 0b0010)
    }
}

class DinnerAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        onReceiveHandler(context, 0b0100)
    }
}

class SleepAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        onReceiveHandler(context, 0b1000)
        //TODO 마지막 날 PillCheck 밀어버리는 로직 추가
    }
}

private fun onReceiveHandler(context: Context?, tid: Int){
    val timeString = when(tid){
        0b0001 -> "morning"
        0b0010 -> "lunch"
        0b0100 -> "dinner"
        0b1000 -> "sleep"
        else -> "error"
    }
    val timeBeforeString = when(tid){
        0b0001 -> "sleep"
        0b0010 -> "morning"
        0b0100 -> "lunch"
        0b1000 -> "dinner"
        else -> "error"
    }

    val sharedPreferences = context?.getSharedPreferences("EventLock", Context.MODE_PRIVATE)
    val timeLock = sharedPreferences?.getBoolean(timeString, false)
    if (timeLock == true) return
    //Event 중복방지
    sharedPreferences?.edit {
        putBoolean(timeBeforeString, false)
        putBoolean(timeString, true)
    }
    handlePillLight(context, tid)
}

private fun handlePillLight(context: Context?, tid: Int){
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