package com.example.pill_checker.repo

import com.example.pill_checker.dao.MainDatabase

class DateTimeRepo(private val database: MainDatabase.MainDatabase) {
    private val dateTimeDao = database.dateTimeDao()

    fun getDateTimeChecked(dtid: Long) = dateTimeDao.getDateTimeById(dtid).checked
}