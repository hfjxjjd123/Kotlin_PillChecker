package com.example.pill_checker.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.PillLight

@Dao
interface PillLightDao {
    @Query("SELECT * FROM PillLight")
    suspend fun getAllPillLights(): List<PillLight>

    @Query("SELECT * FROM PillLight WHERE pid = :pid AND tid = :tid")
    suspend fun getPillLightByPid(pid: Long, tid: Int): PillLight

    @Query("SELECT * FROM PillLight WHERE tid = :tid")
    suspend fun getPillLightsByTid(tid: Int): List<PillLight>

    @Insert
    suspend fun insertPillLight(pillLight: PillLight)

    @Update
    suspend fun updatePillLight(pillLight: PillLight)

    @Query("Delete FROM PillLight WHERE pid = :pid")
    suspend fun deletePillLights(pid: Long)
}
