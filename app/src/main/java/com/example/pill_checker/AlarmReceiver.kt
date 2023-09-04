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
        0b0001 -> Pair("morning", "sleep")
        0b0010 -> Pair("lunch", "morning")
        0b0100 -> Pair("dinner", "lunch")
        0b1000 -> Pair("sleep", "dinner")
        else -> Pair("error", "error")
    }

    val sharedPreferences = context?.getSharedPreferences("EventLock", Context.MODE_PRIVATE)
    val timeLock = sharedPreferences?.getBoolean(timeString.first, false)
    if (timeLock == true) return
    //Event 중복방지
    sharedPreferences?.edit {
        putBoolean(timeString.second, false)
        putBoolean(timeString.first, true)
    }
    //PillCheck 생성
    handlePillLight(context, tid)
}

private fun handlePillLight(context: Context?, tid: Int){
    if(context != null){
        val db = MainDatabase.getDatabase(context.applicationContext)
        val pillCheckRepo = PillCheckRepo(db)
        val timeRepo = TimeRepo(db)
        CoroutineScope(Dispatchers.IO).launch {
            val dtidNow = DateTimeManager.getDateTimeValueNow()
            val pillLightsNext = pillCheckRepo.getPillLightsByTid(dtidNow.and(0b1111).toInt())
            if(pillLightsNext.isNotEmpty()){
                val consideredDtid = timeRepo.pastLastDtid(dtidNow)
                if(consideredDtid != null){
                    val pillLights = pillCheckRepo.getPillLightsByTid(consideredDtid.and(0b1111).toInt())
                    pillCheckRepo.pillLightToPillChecked(pillLights)
                }
                if(dtidNow.and(0b1111).toInt() == 0b0001){
                    removeLastPillCheck(context)
                }
                //TODO push notification
            }
        }
    }
}

private fun removeLastPillCheck(context: Context){
    val db = MainDatabase.getDatabase(context.applicationContext)
    val pillCheckRepo = PillCheckRepo(db)
    CoroutineScope(Dispatchers.IO).launch {
        pillCheckRepo.delete7AgoPillChecks()
    }

}