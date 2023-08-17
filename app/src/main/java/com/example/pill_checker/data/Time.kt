package com.example.pill_checker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Time")
data class Time(
    @PrimaryKey val tid: Int,
    var count: Int
    )
