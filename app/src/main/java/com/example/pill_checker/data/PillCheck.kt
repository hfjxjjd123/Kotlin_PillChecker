package com.example.pill_checker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = DateTime::class, parentColumns = ["dtid"], childColumns = ["dtid"])])
data class PillCheck(
    @PrimaryKey val pid: Long,
    var name: String,
    var checked: Boolean,
    val dtid: Long
)
