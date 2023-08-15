package com.example.pill_checker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DateTime(
    @PrimaryKey val dtid: Long,
    var checked: Boolean
)
