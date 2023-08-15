package com.example.pill_checker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.PillCheck

@Dao
interface PillCheckDao {
    @Query("SELECT * FROM PillCheck WHERE dtid = :dtid")
    fun getAllPillChecksByDtid(dtid: Long): List<PillCheck>

    @Query("SELECT * FROM PillCheck WHERE pid = :pid AND dtid = :dtid")
    fun getPillCheckByPidAndDtid(pid: Long, dtid: Long): PillCheck

    @Insert
    fun insertPillCheck(pillCheck: PillCheck)

    @Update
    fun updatePillCheck(pillCheck: PillCheck)

    @Query("DELETE FROM PillCheck WHERE dtid = :dtid")
    fun deleteAllPillChecksByDtid(dtid: Long)
}