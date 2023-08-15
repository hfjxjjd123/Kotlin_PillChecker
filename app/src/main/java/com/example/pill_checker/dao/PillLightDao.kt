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
    fun getAllPillLights(): List<PillLight>

    @Query("SELECT * FROM PillLight WHERE pid = :pid")
    fun getPillLightByPid(pid: Long): PillLight

    @Insert
    fun insertPillLight(pillLight: PillLight)

    @Update
    fun updatePillLight(pillLight: PillLight)

    @Query("Delete FROM PillLight WHERE pid = :pid")
    fun deletePillLight(pid: Long)
}
