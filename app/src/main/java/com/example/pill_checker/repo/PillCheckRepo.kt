package com.example.pill_checker.repo

import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.DateTime
import com.example.pill_checker.data.PillCheck

class PillCheckRepo(private val database: MainDatabase.MainDatabase) {
    private val dateTimeDao = database.dateTimeDao()
    private val pillCheckDao = database.pillCheckDao()
    private val pillLightDao = database.pillLightDao()

    fun getPillChecksByDtid(dtid: Long) = pillCheckDao.getAllPillChecksByDtid(dtid)
    fun createNextPillChecks(dtid: Long){
        //TODO 수정
        val time: Int = 0b1000
        val pillLights = pillLightDao.getPillLightsByTid(time)

        val dateTime = DateTime(dtid = dtid, checked = false)
        dateTimeDao.insertDateTime(dateTime)

        for (pillLight in pillLights) {
            val pid = pillLight.pid
            val name = pillLight.name

            val pillCheck = PillCheck(pid = pid, name = name, dtid = dtid, checked = false)
            pillCheckDao.insertPillCheck(pillCheck)
        }
    }
    fun updatePillCheck(pid: Long, dtid: Long, checked: Boolean){
        val pillCheck = pillCheckDao.getPillCheckByPidAndDtid(pid, dtid)
        pillCheck.checked = checked
        pillCheckDao.updatePillCheck(pillCheck)

        val dateTime = dateTimeDao.getDateTimeById(dtid)
        dateTime.checked = true
        val pillChecks = pillCheckDao.getAllPillChecksByDtid(dtid)
        for(pill in pillChecks){
            if(!pill.checked){
                dateTime.checked = false
                break
            }
        }
        dateTimeDao.updateDateTime(dateTime)

    }

}