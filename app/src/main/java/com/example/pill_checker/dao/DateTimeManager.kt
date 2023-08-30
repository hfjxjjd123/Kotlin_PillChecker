package com.example.pill_checker.dao

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pill_checker.data.DateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.Calendar

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

val timeIter: List<Int> = listOf(0b0001, 0b0010, 0b0100, 0b1000)

class DateTimeManager {
    companion object {
        fun getDateValue(datetime: LocalDateTime): Long {
            val consideredDate = datetime
                .minusHours(DURATION.toLong() + HOUR_SLEEP.toLong())
                .minusMinutes(MIN_SLEEP.toLong())

            return ChronoUnit.DAYS.between(LocalDate.parse(DATE_BEGINNING), consideredDate)
        }

        fun getTimeValue(datetime: LocalDateTime): Int {
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

        fun getTimeValueExtended(datetime: LocalDateTime): Int {
            val startOfDateTime = getDate(datetime).atStartOfDay()
            val startOfMorning = startOfDateTime.plusHours(HOUR_MORNING.toLong() + DURATION)
                .plusMinutes(MIN_MORNING.toLong())
            val startOfLunch = startOfDateTime.plusHours(HOUR_LUNCH.toLong() + DURATION)
                .plusMinutes(MIN_LUNCH.toLong())
            val startOfDinner = startOfDateTime.plusHours(HOUR_DINNER.toLong() + DURATION)
                .plusMinutes(MIN_DINNER.toLong())

            return if (datetime.isBefore(startOfMorning)) 0b1001
            else if (datetime.isBefore(startOfMorning.plusHours(DURATION.toLong()))) 0b0001
            else if (datetime.isBefore(startOfLunch)) 0b0011
            else if (datetime.isBefore(startOfLunch.plusHours(DURATION.toLong()))) 0b0010
            else if (datetime.isBefore(startOfDinner)) 0b0110
            else if (datetime.isBefore(startOfDinner.plusHours(DURATION.toLong()))) 0b0100
            else if (datetime.isBefore(startOfDateTime.plusHours(24).minusHours(DURATION.toLong()))) 0b1100
            else 0b1000
        }

        fun countTimeBit(bit: Int): Int{
            var count = 0
            var jit = bit
            for(i in 0..3){
                if(jit.and(0b1) == 1) count++
                jit = jit.shr(1)
            }
            return count
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

        fun getDateTimeValueWhenEnd(): Long{
            val startOfTime = LocalDateTime.now().minusHours(2)

            val date = getDateValue(startOfTime)
            val time = getTimeValue(startOfTime)

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

        fun getDateBeforeString(dateDiff: Long): String {
            return when (dateDiff) {
                -1L -> "오류"
                0L -> "오늘"
                1L -> "어제"
                else -> dateDiff.toString() + "일전"
            }
        }

        fun getTimeString(time: Int): String {
            return when (time) {
                0b0001 -> "아침"
                0b0010 -> "점심"
                0b0100 -> "저녁"
                0b1000 -> "취침전"
                else -> "오류"
            }
        }
    }
}

class CalendarManager(){
    companion object{
        fun getMorningCalendar(): Calendar{
            val morningCalendar: Calendar = Calendar.getInstance()
            morningCalendar.set(Calendar.HOUR_OF_DAY, HOUR_MORNING)
            morningCalendar.set(Calendar.MINUTE, MIN_MORNING)
            return morningCalendar
        }
        fun getLunchCalendar(): Calendar{
            val lunchCalendar: Calendar = Calendar.getInstance()
            lunchCalendar.set(Calendar.HOUR_OF_DAY, HOUR_LUNCH)
            lunchCalendar.set(Calendar.MINUTE, MIN_LUNCH)
            return lunchCalendar
        }
        fun getDinnerCalendar(): Calendar{
            val dinnerCalendar: Calendar = Calendar.getInstance()
            dinnerCalendar.set(Calendar.HOUR_OF_DAY, HOUR_DINNER)
            dinnerCalendar.set(Calendar.MINUTE, MIN_DINNER)
            return dinnerCalendar
        }
        //TODO Change sleep time
        fun getSleepCalendar(): Calendar{
            val sleepCalendar: Calendar = Calendar.getInstance()
            sleepCalendar.set(Calendar.HOUR_OF_DAY, HOUR_SLEEP)
            sleepCalendar.set(Calendar.MINUTE, MIN_SLEEP)
            return sleepCalendar
        }
    }
}