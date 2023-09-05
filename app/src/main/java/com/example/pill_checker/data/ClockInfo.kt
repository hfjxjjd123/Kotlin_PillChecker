package com.example.pill_checker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ClockInfo")
data class ClockInfo(
    @PrimaryKey val cid: Long,
    var morningHour: Int,
    var morningMin: Int,
    var lunchHour: Int,
    var lunchMin: Int,
    var dinnerHour: Int,
    var dinnerMin: Int,
    var nightHour: Int,
    var nightMin: Int
)
