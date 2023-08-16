package com.example.pill_checker

import android.app.Application
import androidx.room.Room
import com.example.pill_checker.dao.MainDatabase

class MyApplication: Application(){
    val database: MainDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            MainDatabase::class.java,
            "main_database"
        ).build()
    }
}