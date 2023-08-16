package com.example.pill_checker.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class Pill(
    @PrimaryKey val pid: Long,
    var name: String,
    var times: Int,
    @TypeConverters(BitmapTypeConverter::class)
    var image: Bitmap?,
    var ea: Int?,
)
