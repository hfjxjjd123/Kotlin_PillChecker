package com.example.pill_checker.dao

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Calendar

const val DATE_BEGINNING = "2021-01-01"

val timeIter: List<Int> = listOf(0b0001, 0b0010, 0b0100, 0b1000)

class DateTimeManager {
    companion object {

        fun getTimeValue(datetime: LocalDateTime): Int {
            val startOfDateTime = getDate(datetime).atStartOfDay()

            val startOfMorning = startOfDateTime.plusHours(CalendarManager.getMorningHour().toLong())
                .plusMinutes(CalendarManager.getMorningMin().toLong())
            val startOfLunch = startOfDateTime.plusHours(CalendarManager.getLunchHour().toLong())
                .plusMinutes(CalendarManager.getLunchMin().toLong())
            val startOfDinner = startOfDateTime.plusHours(CalendarManager.getDinnerHour().toLong())
                .plusMinutes(CalendarManager.getDinnerMin().toLong())
            val startOfSleep = startOfDateTime.plusDays(1).plusHours(CalendarManager.getSleepHourRel().toLong())
                .plusMinutes(CalendarManager.getSleepMinAbs().toLong())

            return if (datetime.isBefore(startOfMorning)) 0b1000
            else if (datetime.isBefore(startOfLunch)) 0b0001
            else if (datetime.isBefore(startOfDinner)) 0b0010
            else if (datetime.isBefore(startOfSleep)) 0b0100
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
            return datetime.toLocalDate()
        }
        //morning 시간이 되지 않는 이상 전날로 처리됨
        fun getDateValueConsidered(datetime: LocalDateTime): Long {
            val consideredDate = datetime
                .minusHours(CalendarManager.getMorningHour().toLong())
                .minusMinutes(CalendarManager.getMorningMin().toLong())

            return ChronoUnit.DAYS.between(LocalDate.parse(DATE_BEGINNING), consideredDate)
        }

        fun getDateTimeValueNow(): Long {
            val now = LocalDateTime.now()

            val date = getDateValueConsidered(now)
            val time = getTimeValue(now)
            return getDateTimeValue(date, time)
        }

        fun getDateTimeValueWhenEnd(): Long{
            val startOfTime = LocalDateTime.now().minusHours(2)

            val date = getDateValueConsidered(startOfTime)
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

class CalendarManager{
    companion object{
        private var HOUR_MORNING = 8
        private var MIN_MORNING = 0
        private var HOUR_LUNCH = 12
        private var MIN_LUNCH = 30
        private var HOUR_DINNER = 18
        private var MIN_DINNER = 0
        private var HOUR_SLEEP = 0
        private var MIN_SLEEP = -30

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
        fun getSleepCalendar(): Calendar{
            val sleepCalendar: Calendar = Calendar.getInstance()
            sleepCalendar.set(Calendar.HOUR_OF_DAY, getSleepHourAbs())
            sleepCalendar.set(Calendar.MINUTE, getSleepMinAbs())
            return sleepCalendar
        }

        fun getMorningHour(): Int = HOUR_MORNING
        fun getMorningMin(): Int = MIN_MORNING
        fun getLunchHour(): Int = HOUR_LUNCH
        fun getLunchMin(): Int = MIN_LUNCH
        fun getDinnerHour(): Int = HOUR_DINNER
        fun getDinnerMin(): Int = MIN_DINNER
        fun getSleepHourRel(): Int = HOUR_SLEEP
        fun getSleepMinRel(): Int = MIN_SLEEP
        fun getSleepHourAbs(): Int{
            val sleepHourRel = HOUR_SLEEP
            return if(sleepHourRel < 0) 24 + sleepHourRel
            else sleepHourRel
        }
        fun getSleepMinAbs(): Int{
            val sleepMinRel = MIN_SLEEP
            return if(sleepMinRel < 0) 60 + sleepMinRel
            else sleepMinRel
        }

        fun setMorningHour(hour: Int){ HOUR_MORNING = hour }
        fun setMorningMin(min: Int){ MIN_MORNING = min }
        fun setLunchHour(hour: Int){ HOUR_LUNCH = hour }
        fun setLunchMin(min: Int){ MIN_LUNCH = min }
        fun setDinnerHour(hour: Int){ HOUR_DINNER = hour }
        fun setDinnerMin(min: Int){ MIN_DINNER = min }
        fun setSleepHour(hour: Int){ HOUR_SLEEP = hour }
        fun setSleepMin(min: Int){ MIN_SLEEP = min }
    }
}