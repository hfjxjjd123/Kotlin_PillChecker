package com.example.pill_checker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.DateTime

@Dao
interface DateTimeDao {
    @Query("SELECT * FROM DateTime")
    suspend fun getAllDateTimes(): List<DateTime>

    @Query("SELECT * FROM DateTime WHERE dtid = :dtid")
    suspend fun getDateTimeById(dtid: Long): DateTime?

    @Insert
    suspend fun insertDateTime(dateTime: DateTime)

    @Query("DELETE FROM DateTime WHERE dtid = :id")
    suspend fun deleteDateTimeById(id: Long)

    @Update
    suspend fun updateDateTime(dateTime: DateTime)

}