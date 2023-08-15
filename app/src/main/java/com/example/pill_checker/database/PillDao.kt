package com.example.pill_checker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.Pill

@Dao
interface PillDao {
    @Query("SELECT * FROM pill")
    fun getAllPills(): List<Pill>

    @Query("SELECT * FROM pill WHERE pid = :id")
    fun getPillById(id: Long): Pill

    @Insert
    fun insertPill(pill: Pill)

    @Update
    fun updatePill(pill: Pill)

    @Query("DELETE FROM pill WHERE pid = :id")
    fun deletePillById(id: Long)

}