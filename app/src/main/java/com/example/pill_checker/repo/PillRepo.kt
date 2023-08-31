package com.example.pill_checker.repo

import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.dao.timeIter
import com.example.pill_checker.data.Pill
import com.example.pill_checker.data.PillLight
import java.time.LocalDateTime


class PillRepo(private val database: MainDatabase){
    private val pillDao = database.pillDao()
    private val pillLightDao = database.pillLightDao()
    private val timeDao = database.timeDao()
    private val pillCheckRepo = PillCheckRepo(database)
    private val timeRepo = TimeRepo(database)

    suspend fun getPillById(id: Long) = pillDao.getPillById(id)
    suspend fun getAllPills() = pillDao.getAllPills()
    suspend fun createPill(pill: Pill) {
        val pid = pillDao.insertPill(pill)
        val pillNew = pillDao.getPillById(pid)

        val tidNow = DateTimeManager.getTimeValue(LocalDateTime.now())
        val countAfter: Int = timeDao.getTimeById(tidNow).count
        if(countAfter == 0 && tidNow.and(pillNew.times) == tidNow){
            val consideredTid = timeRepo.pastLastTid(tidNow)
            if(consideredTid != null){
                val pillLights = pillLightDao.getPillLightsByTid(consideredTid)
                pillCheckRepo.pillLightToPillChecked(pillLights)
            }
        }
        createPillLights(pillNew)
    }

    suspend fun updatePill(pill: Pill, timesBefore: Int) {
        pillDao.updatePill(pill)
        deletePillLights(pill.pid, timesBefore)
        createPillLights(pill)
    }

    suspend fun deletePill(pill: Pill) {
        pillDao.deletePillById(pill.pid)
        deletePillLights(pill.pid, pill.times)
    }

    suspend fun createPillLights(pill: Pill){
        val times = pill.times
        // 0001 -> 아침, 0010 -> 점심, 0100 -> 저녁, 1000 -> 취침
        for (bit in timeIter){
            if(times and bit == bit){
                val pillLight = PillLight(pid = pill.pid, tid = bit, name = pill.name)
                pillLightDao.insertPillLight(pillLight)
                countTime(bit)
            }
        }
    }

    suspend fun deletePillLights(pid: Long, beforeTimes: Int){
        pillLightDao.deletePillLights(pid)
        countDownTime(beforeTimes)
    }

    suspend fun countTime(time: Int){
        val time = timeDao.getTimeById(time)
        time.count++
        timeDao.updateTime(time)
    }
    suspend fun countDownTime(timesBefore: Int){
        for (bit in timeIter){
            if(timesBefore and bit == bit){
                val time = timeDao.getTimeById(bit)
                time.count--
                timeDao.updateTime(time)
            }
        }
    }

}