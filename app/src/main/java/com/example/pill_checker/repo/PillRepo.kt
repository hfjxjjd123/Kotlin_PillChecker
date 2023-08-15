package com.example.pill_checker.repo

import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.Pill
import com.example.pill_checker.data.PillLight


class PillRepo(private val database: MainDatabase.MainDatabase){
    private val pillDao = database.pillDao()
    private val pillLightDao = database.pillLightDao()

    fun getPillById(id: Long) = pillDao.getPillById(id)
    fun getAllPills() = pillDao.getAllPills()
    fun createPill(pill: Pill) {
        pillDao.insertPill(pill)
        createPillLight(pill)
    }
    fun updatePill(pill: Pill) {
        pillDao.updatePill(pill)
        pillLightDao.deletePillLights(pill.pid)
        createPillLight(pill)
    }

    fun deletePill(pill: Pill) {
        pillDao.deletePillById(pill.pid)
        pillLightDao.deletePillLights(pill.pid)
    }

    fun createPillLight(pill: Pill){
        val times = pill.times
        // 0001 -> 아침, 0010 -> 점심, 0100 -> 저녁, 1000 -> 취침
        for (bit in 0b0001..0b1000 step 0b0010){
            if(times and bit == bit){
                val pillLight = PillLight(pid = pill.pid, tid = bit, name = pill.name)
                pillLightDao.insertPillLight(pillLight)
            }
        }
    }

}