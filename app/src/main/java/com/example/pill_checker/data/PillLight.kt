package com.example.pill_checker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// 외래키가 DataClass안에 포함되어있어야하나?
@Entity(
    tableName = "PillLight",
    primaryKeys = ["pid", "tid"],
    foreignKeys = [ForeignKey(entity = Time::class, parentColumns = ["tid"], childColumns = ["tid"])]
)
data class PillLight(
    val pid: Long,
    val name: String,
    val tid: Int
    )
