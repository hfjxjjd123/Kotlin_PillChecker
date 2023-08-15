package com.example.pill_checker.repo

import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.dao.PillDao
import com.example.pill_checker.data.Pill


class PillRepo(private val database: MainDatabase.MainDatabase){
    private val pillDao = database.pillDao()

    fun getPillById(id: Long) = pillDao.getPillById(id)
    fun getAllPills() = pillDao.getAllPills()
    fun createPill(pill: Pill) = pillDao.insertPill(pill)
    fun updatePill(pill: Pill) = pillDao.updatePill(pill)
    fun deletePill(pill: Pill) = pillDao.deletePillById(pill.pid)

}