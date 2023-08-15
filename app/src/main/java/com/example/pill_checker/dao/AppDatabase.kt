package com.example.pill_checker.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pill_checker.data.*

abstract class AppDatabase {
    @Database(
        entities = [Pill::class, PillCheck::class, PillLight::class, DateTime::class, Time::class],
        version = 1
    )
    abstract class AppDatabase : RoomDatabase() {
        abstract fun pillDao(): PillDao
        abstract fun pillCheckDao(): PillCheckDao
        abstract fun pillLightDao(): PillLightDao
        abstract fun dateTimeDao(): DateTimeDao
        abstract fun timeDao(): TimeDao

        companion object {
            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getDatabase(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }

}