package com.example.pill_checker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.Time

@Dao
interface ClockInfoDao {
    @Query("SELECT * FROM ClockInfo WHERE cid = :cid")
    suspend fun getTimeById(cid: Int): Time
    @Update
    suspend fun updateTime(time: Time)
    @Insert
    suspend fun insertTime(time: Time)
}