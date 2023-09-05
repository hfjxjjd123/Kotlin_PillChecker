package com.example.pill_checker.repo

import com.example.pill_checker.dao.CalendarManager
import com.example.pill_checker.dao.MainDatabase
import java.util.*

class ClockInfoRepo(private val database: MainDatabase) {
    private val clockInfoDao = database.clockInfoDao()

    //cid는 항상 0, 유일
    suspend fun getCalendarInfo() = clockInfoDao.getClockInfoById(0)

    suspend fun getMorningInfo(): Pair<Int, Int>{
        val clockInfo = clockInfoDao.getClockInfoById(0)
        val morningHour = clockInfo.morningHour
        val morningMin = clockInfo.morningMin
        return Pair(morningHour, morningMin)
    }
    suspend fun getLunchInfo(): Pair<Int, Int>{
        val clockInfo = clockInfoDao.getClockInfoById(0)
        val lunchHour = clockInfo.lunchHour
        val lunchMin = clockInfo.lunchMin
        return Pair(lunchHour, lunchMin)
    }
    suspend fun getDinnerInfo(): Pair<Int, Int>{
        val clockInfo = clockInfoDao.getClockInfoById(0)
        val dinnerHour = clockInfo.dinnerHour
        val dinnerMin = clockInfo.dinnerMin
        return Pair(dinnerHour, dinnerMin)
    }
    suspend fun getSleepInfo(): Pair<Int, Int>{
        val clockInfo = clockInfoDao.getClockInfoById(0)
        val sleepHour = clockInfo.sleepHour
        val sleepMin = clockInfo.sleepMin
        return Pair(sleepHour, sleepMin)
    }


    suspend fun getMorningCalendar(): Calendar {
        val morningClock = getMorningInfo()
        val morningCalendar: Calendar = Calendar.getInstance()
        morningCalendar.set(Calendar.HOUR_OF_DAY, morningClock.first)
        morningCalendar.set(Calendar.MINUTE, morningClock.second)
        return morningCalendar
    }
    suspend fun getLunchCalendar(): Calendar{
        val lunchClock = getLunchInfo()
        val lunchCalendar: Calendar = Calendar.getInstance()
        lunchCalendar.set(Calendar.HOUR_OF_DAY, lunchClock.first)
        lunchCalendar.set(Calendar.MINUTE, lunchClock.second)
        return lunchCalendar
    }
    suspend fun getDinnerCalendar(): Calendar{
        val dinnerClock = getDinnerInfo()
        val dinnerCalendar: Calendar = Calendar.getInstance()
        dinnerCalendar.set(Calendar.HOUR_OF_DAY, dinnerClock.first)
        dinnerCalendar.set(Calendar.MINUTE, dinnerClock.second)
        return dinnerCalendar
    }

    suspend fun getSleepCalendar(): Calendar{
        val sleepClock = getSleepInfo()
        val sleepClockAbs = getSleepTimeAbs(sleepClock.first, sleepClock.second)
        val sleepCalendar: Calendar = Calendar.getInstance()

        sleepCalendar.set(Calendar.HOUR_OF_DAY, sleepClockAbs.first)
        sleepCalendar.set(Calendar.MINUTE, sleepClockAbs.second)
        return sleepCalendar
    }

    fun getSleepTimeAbs(sleepHourRel: Int, sleepMinRel: Int): Pair<Int,Int>{
        var isMinNegative = false
        val sleepMinAbs: Int
        val sleepHourAbs: Int
        var sleepHour: Int = sleepHourRel

        if(sleepMinRel < 0){
            isMinNegative = true
            sleepMinAbs = 60 + sleepMinRel
        }else{
            sleepMinAbs = sleepMinRel
        }

        if(isMinNegative) sleepHour -= 1
        if(sleepHour < 0){
            sleepHourAbs = 24+sleepHour
        }else{
            sleepHourAbs = sleepHour
        }
        return Pair(sleepHourAbs, sleepMinAbs)
    }
}