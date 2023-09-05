package com.example.pill_checker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.ClockInfo
import com.example.pill_checker.data.Time

@Dao
interface ClockInfoDao {
    @Query("SELECT * FROM ClockInfo WHERE cid = :cid")
    suspend fun getClockInfoById(cid: Int): ClockInfo
    @Update
    suspend fun updateTime(clockInfo: ClockInfo)
    @Insert
    suspend fun insertTime(clockInfo: ClockInfo)
}