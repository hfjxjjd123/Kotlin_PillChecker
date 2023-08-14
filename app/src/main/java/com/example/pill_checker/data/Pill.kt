package com.example.pill_checker.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pill(
    @PrimaryKey val pid: Long,
    var name: String,
    var times: Int,
    var image: Bitmap?,
    var ea: Int?,
)
