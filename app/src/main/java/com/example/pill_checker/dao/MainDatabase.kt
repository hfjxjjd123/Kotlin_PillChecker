package com.example.pill_checker.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pill_checker.CalendarActivity2
import com.example.pill_checker.data.*

abstract class MainDatabase {
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

        companion object {
            @Volatile
            private var INSTANCE: MainDatabase? = null

            fun getDatabase(context: CalendarActivity2): MainDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java,
                        "main_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }

}