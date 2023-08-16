package com.example.pill_checker.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pill_checker.CalendarActivity2
import com.example.pill_checker.adapter.CheckRecyclerAdapter
import com.example.pill_checker.data.*

@Database(
    entities = [Pill::class, PillCheck::class, PillLight::class, DateTime::class, Time::class],
    version = 1
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun pillDao(): PillDao
    abstract fun pillCheckDao(): PillCheckDao
    abstract fun pillLightDao(): PillLightDao
    abstract fun dateTimeDao(): DateTimeDao
    abstract fun timeDao(): TimeDao
}