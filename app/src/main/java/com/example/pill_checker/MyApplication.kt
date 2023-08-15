package com.example.pill_checker

import android.app.Application
import com.example.pill_checker.dao.AppDatabase

class MyApplication: Application(){
    val database by lazy { AppDatabase.AppDatabase.getDatabase(this) }
}