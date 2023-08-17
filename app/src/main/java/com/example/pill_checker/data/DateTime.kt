package com.example.pill_checker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DateTime")
data class DateTime(
    @PrimaryKey val dtid: Long,
    var checked: Boolean
)
