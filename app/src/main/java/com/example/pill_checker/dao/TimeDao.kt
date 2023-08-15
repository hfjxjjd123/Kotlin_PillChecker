package com.example.pill_checker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.Time

@Dao
interface TimeDao {
    @Query("SELECT * FROM Time WHERE tid = :tid")
    fun getTimeById(tid: Long): Time

    @Update
    fun updateTime(time: Time)

    @Insert
    fun insertTime(time: Time)
}