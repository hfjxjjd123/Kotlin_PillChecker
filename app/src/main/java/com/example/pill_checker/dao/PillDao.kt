package com.example.pill_checker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.Pill

@Dao
interface PillDao {
    @Query("SELECT * FROM pill")
    suspend fun getAllPills(): List<Pill>

    @Query("SELECT * FROM pill WHERE pid = :pid")
    suspend fun getPillById(pid: Long): Pill

    @Insert
    suspend fun insertPill(pill: Pill)

    @Update
    suspend fun updatePill(pill: Pill)

    @Query("DELETE FROM pill WHERE pid = :pid")
    suspend fun deletePillById(pid: Long)

}