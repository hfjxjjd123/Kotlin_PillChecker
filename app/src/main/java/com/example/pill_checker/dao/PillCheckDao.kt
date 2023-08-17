package com.example.pill_checker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.PillCheck

@Dao
interface PillCheckDao {
    @Query("SELECT * FROM PillCheck WHERE dtid = :dtid")
    suspend fun getAllPillChecksByDtid(dtid: Long): List<PillCheck>

    @Query("SELECT * FROM PillCheck WHERE pid = :pid AND dtid = :dtid")
    suspend fun getPillCheckByPidAndDtid(pid: Long, dtid: Long): PillCheck

    @Insert
    suspend fun insertPillCheck(pillCheck: PillCheck)

    @Update
    suspend fun updatePillCheck(pillCheck: PillCheck)

    @Query("DELETE FROM PillCheck WHERE dtid = :dtid")
    suspend fun deleteAllPillChecksByDtid(dtid: Long)
}