package com.example.pill_checker.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pill_checker.CalendarActivity2
import com.example.pill_checker.adapter.CheckRecyclerAdapter
import com.example.pill_checker.data.*

@Database(
    entities = [Pill::class, PillCheck::class, PillLight::class, DateTime::class, Time::class],
    version = 2
)
@TypeConverters(BitmapTypeConverter::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun pillDao(): PillDao
    abstract fun pillCheckDao(): PillCheckDao
    abstract fun pillLightDao(): PillLightDao
    abstract fun dateTimeDao(): DateTimeDao
    abstract fun timeDao(): TimeDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}