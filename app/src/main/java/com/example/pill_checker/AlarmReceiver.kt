package com.example.pill_checker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pill_checker.dao.*
import java.time.LocalDateTime
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    //CALLED AFTER THE TIME IS
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)

        // Calculate the time for the new alarm
        val time = DateTimeManager.getTimeValueExtended(LocalDateTime.now())
        val isStart = DateTimeManager.countTimeBit(time) == 1

        val calendar = Calendar.getInstance()
        if(isStart){
            when(time) {
                //TODO TO CALL NEXT ALARM
                0b0001 -> {
                    //다음 알람 적용
                    calendar.set(Calendar.HOUR_OF_DAY, HOUR_MORNING + DURATION)
                    calendar.set(Calendar.MINUTE, MIN_MORNING)
                }
                0b0010 -> {
                    calendar.set(Calendar.HOUR_OF_DAY, HOUR_LUNCH + DURATION)
                    calendar.set(Calendar.MINUTE, MIN_LUNCH)
                }
                0b0100 -> {
                    calendar.set(Calendar.HOUR_OF_DAY, HOUR_DINNER + DURATION)
                    calendar.set(Calendar.MINUTE, MIN_DINNER)
                }
                0b1000 -> {
                    //TODO DAY OVER CONTROLL
                    //정리
                    //NextDay면 Panel은 일단 기존 패널을 사용할 것임
                    //NO PANEL UPDATE! Day가 바뀔 때 다시 업데이트 될 것이라는 점 감안, 무시하기
                    calendar.set(Calendar.HOUR_OF_DAY, HOUR_SLEEP + DURATION)
                    calendar.set(Calendar.MINUTE, MIN_SLEEP)
                }
            }
        }else if(!isStart) {
            when (time) {
                //새벽
                0b1001 -> {

                }
                0b0011 -> {

                }
                0b0110 -> {

                }
                0b1100 -> {

                }
                else -> {

                }
            }
        }

        // Set the new alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}