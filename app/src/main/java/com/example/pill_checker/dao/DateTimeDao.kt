package com.example.pill_checker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pill_checker.data.DateTime

@Dao
interface DateTimeDao {
    @Query("SELECT * FROM DateTime")
    fun getAllDateTimes(): List<DateTime>

    @Query("SELECT * FROM DateTime WHERE dtid = :dtid")
    fun getDateTimeById(dtid: Long): DateTime?

    @Insert
    fun insertDateTime(dateTime: DateTime)

    @Query("DELETE FROM DateTime WHERE dtid = :id")
    fun deleteDateTimeById(id: Long)

    @Update
    fun updateDateTime(dateTime: DateTime)

}