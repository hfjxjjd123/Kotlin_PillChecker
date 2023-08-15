package com.example.pill_checker.database

import androidx.room.Dao
import androidx.room.Query
import com.example.pill_checker.data.PillCheck

@Dao
interface PillCheckDao {
    @Query("SELECT * FROM PillCheck")
    fun getAllPillChecks(): List<PillCheck>


}