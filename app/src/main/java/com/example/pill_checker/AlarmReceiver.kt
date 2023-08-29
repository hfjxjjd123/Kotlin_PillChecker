package com.example.pill_checker

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.pill_checker.dao.*
import java.time.LocalDateTime
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    //CALLED AFTER THE TIME IS
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        // Calculate the time for the new alarm
        val time = DateTimeManager.getTimeValueExtended(LocalDateTime.now())
        val isStart = DateTimeManager.countTimeBit(time) == 1
        var timeText = ""

        val calendar = Calendar.getInstance()
        if(isStart){
            //다음 알람 시간이 검증됨
            //패널 업데이트가 검증됨
            when(time) {
                //TODO TO CALL NEXT ALARM
                //현재 패널 체킹은 불필요
                0b0001 -> {
                    //다음 알람 적용
                    timeText = "아침"
                    calendar.set(Calendar.HOUR_OF_DAY, HOUR_MORNING + DURATION)
                    calendar.set(Calendar.MINUTE, MIN_MORNING)
                }
                0b0010 -> {
                    timeText = "점심"
                    calendar.set(Calendar.HOUR_OF_DAY, HOUR_LUNCH + DURATION)
                    calendar.set(Calendar.MINUTE, MIN_LUNCH)
                }
                0b0100 -> {
                    timeText = "저녁"
                    calendar.set(Calendar.HOUR_OF_DAY, HOUR_DINNER + DURATION)
                    calendar.set(Calendar.MINUTE, MIN_DINNER)
                }
                0b1000 -> {
                    timeText = "자기전"
                    //TODO DAY OVER CONTROLL
                    //정리
                    //NextDay면 Panel은 일단 기존 패널을 사용할 것임
                    //NO PANEL UPDATE! Day가 바뀔 때 다시 업데이트 될 것이라는 점 감안, 무시하기
                    calendar.set(Calendar.HOUR_OF_DAY, HOUR_SLEEP + DURATION)
                    calendar.set(Calendar.MINUTE, MIN_SLEEP)
                }
            }
            val notification = createNotification(context, timeText)
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notification)

        }else if(!isStart) {
            //TODO NEXT TIME에 대한 검증이 필요하다.
            //function NEXT를 선언하면, 그 시간에 맞게 다음 알람을 설정할 수 있을듯?
            //그에 맞게 패널을 업데이트 해주면 되고... 다만 SLEEP 이후다라고 한다면? -> 이론상 SLeep 이후는 0b1001이므로 상관노
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

fun createNotification(context: Context, timeText: String): Notification {

    val notificationIntent = Intent(context, MainActivity::class.java)
    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val notificationPendingIntent = PendingIntent.getActivity(
        context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    )

    return NotificationCompat.Builder(context, "PillNotice")
        .setContentTitle("$timeText 약")
        .setContentText("약 먹을 시간이에요")
        .setSmallIcon(R.drawable.pill_image)
        .setContentIntent(notificationPendingIntent) // Set the PendingIntent
        .setAutoCancel(true) // Auto-dismiss the notification when tapped
        .build()
}