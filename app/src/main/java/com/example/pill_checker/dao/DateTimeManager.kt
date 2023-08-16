package com.example.pill_checker.dao

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pill_checker.data.DateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

const val DATE_BEGINNING = "2021-01-01"
const val DURATION = 2

const val HOUR_MORNING = 8
const val MIN_MORNING = 0
const val HOUR_LUNCH = 12
const val MIN_LUNCH = 30
const val HOUR_DINNER = 18
const val MIN_DINNER = 0
const val HOUR_SLEEP = 0
const val MIN_SLEEP = -30

class DateTimeManager {
    fun getDateValue(datetime: LocalDateTime): Long {
        val consideredDate = datetime
            .minusHours(DURATION.toLong() + HOUR_SLEEP.toLong())
            .minusMinutes(MIN_SLEEP.toLong())

        return ChronoUnit.DAYS.between(LocalDate.parse(DATE_BEGINNING), consideredDate)
    }
    fun getTimeValue(datetime: LocalDateTime): Int{
        val startOfDateTime = getDate(datetime).atStartOfDay()
        val endOfMorning = startOfDateTime.plusHours(HOUR_MORNING.toLong() + DURATION)
            .plusMinutes(MIN_MORNING.toLong())
        val endOfLunch = startOfDateTime.plusHours(HOUR_LUNCH.toLong() + DURATION)
            .plusMinutes(MIN_LUNCH.toLong())
        val endOfDinner = startOfDateTime.plusHours(HOUR_DINNER.toLong() + DURATION)
            .plusMinutes(MIN_DINNER.toLong())

        return if (datetime.isBefore(endOfMorning)) 0b0001
        else if (datetime.isBefore(endOfLunch)) 0b0010
        else if (datetime.isBefore(endOfDinner)) 0b0100
        else 0b1000
    }

    fun getDate(datetime: LocalDateTime): LocalDate {
        val consideredDate = datetime
            .minusHours(DURATION.toLong() + HOUR_SLEEP.toLong())
            .minusMinutes(MIN_SLEEP.toLong())
        return consideredDate.toLocalDate()
    }

    fun getDateTimeValueNow(): Long {
        val now = LocalDateTime.now()

        val date = getDateValue(now)
        val time = getTimeValue(now)

        return getDateTimeValue(date, time)
    }

    fun getDateTimeValue(date: Long, time: Int): Long {
        return date.shl(4) + time
    }

    fun separateDateTimeValue(datetimeValue: Long): Pair<Long, Int> {
        val date = datetimeValue.shr(4)
        val time = datetimeValue.and(0b1111)
        return Pair(date, time.toInt())
    }

    fun getDateDiff(date: Long): Long {
        val dateNow = getDate(LocalDateTime.now())
        val dateDiffNow = ChronoUnit.DAYS.between(LocalDate.parse(DATE_BEGINNING), dateNow)
        return dateDiffNow - date
    }

    fun getDateBeforeString(dateDiff: Long): String{
        return when(dateDiff){
            -1L -> "오류"
            0L -> "오늘"
            1L -> "어제"
            else -> dateDiff.toString() + "일전"
        }
    }

    fun getTimeString(time: Int): String{
        return when(time){
            0b0001 -> "아침"
            0b0010 -> "점심"
            0b0100 -> "저녁"
            0b1000 -> "취침전"
            else -> "오류"
        }
    }
}