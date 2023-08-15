package com.example.pill_checker.repo

import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.Pill
import com.example.pill_checker.data.PillLight


class PillRepo(private val database: MainDatabase.MainDatabase){
    private val pillDao = database.pillDao()
    private val pillLightDao = database.pillLightDao()
    private val timeDao = database.timeDao()

    fun getPillById(id: Long) = pillDao.getPillById(id)
    fun getAllPills() = pillDao.getAllPills()
    fun createPill(pill: Pill) {
        pillDao.insertPill(pill)
        createPillLights(pill)
    }
    fun updatePill(pill: Pill, timesBefore: Int) {
        pillDao.updatePill(pill)
        deletePillLights(pill.pid, timesBefore)
        createPillLights(pill)
    }

    fun deletePill(pill: Pill) {
        pillDao.deletePillById(pill.pid)
        deletePillLights(pill.pid, pill.times)
    }

    private fun createPillLights(pill: Pill){
        val times = pill.times
        // 0001 -> 아침, 0010 -> 점심, 0100 -> 저녁, 1000 -> 취침
        for (bit in 0b0001..0b1000 step 0b0010){
            if(times and bit == bit){
                val pillLight = PillLight(pid = pill.pid, tid = bit, name = pill.name)
                pillLightDao.insertPillLight(pillLight)
                countTime(bit)
            }
        }
    }

    private fun deletePillLights(pid: Long, beforeTimes: Int){
        pillLightDao.deletePillLights(pid)
        countDownTime(beforeTimes)
    }

    private fun countTime(time: Int){
        val time = timeDao.getTimeById(time)
        time.count++
        timeDao.updateTime(time)
    }
    private fun countDownTime(timesBefore: Int){
        for (bit in 0b0001..0b1000 step 0b0010){
            if(timesBefore and bit == bit){
                val time = timeDao.getTimeById(bit)
                time.count--
                timeDao.updateTime(time)
            }
        }
    }

}