package com.example.pill_checker.repo

import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.Time

class TimeRepo(private val database: MainDatabase.MainDatabase){
    private val timeDao = database.timeDao()

    fun initialTime(){
        for (bit in 0b0001..0b1000 step 0b0010){
            val time = Time(tid = bit, count = 0)
            timeDao.insertTime(time)
        }
    }
}