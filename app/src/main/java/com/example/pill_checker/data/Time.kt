package com.example.pill_checker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Time(
    @PrimaryKey val tid: Int,
    var count: Int
    )
