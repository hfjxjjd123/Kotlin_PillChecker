package com.example.pill_checker.dao

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

const val DATE_BEGINNING = "2021-01-01"

class DateTimeManager {
    fun getDateValue(date: LocalDate): Long {
        return ChronoUnit.DAYS.between(LocalDate.parse(DATE_BEGINNING), date)
    }
}