package com.example.pill_checker.repo

import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.dao.timeIter
import com.example.pill_checker.data.DateTime
import com.example.pill_checker.data.PillCheck
import java.time.LocalDateTime

class PillCheckRepo(private val database: MainDatabase) {
    private val dateTimeDao = database.dateTimeDao()
    private val pillCheckDao = database.pillCheckDao()
    private val pillLightDao = database.pillLightDao()

    private suspend fun getPillCheckByPidAndDtid(pid: Long, dtid: Long) = pillCheckDao.getPillCheckByPidAndDtid(pid, dtid)
    suspend fun getPillChecksByDtid(dtid: Long) = pillCheckDao.getAllPillChecksByDtid(dtid)
    suspend fun createNextPillChecks(dtid: Long){
        val timeValue: Int = dtid.and(0b1111).toInt()
        val pillLights = pillLightDao.getPillLightsByTid(timeValue)

        val dateTime = DateTime(dtid = dtid, checked = false)
        dateTimeDao.insertDateTime(dateTime)

        for (pillLight in pillLights) {
            val pid = pillLight.pid
            val name = pillLight.name

            val pillCheck = PillCheck(pid = pid, name = name, dtid = dtid, checked = false)
            pillCheckDao.insertPillCheck(pillCheck)
        }
    }

    suspend fun deletePrevPillChecks(dateValue: Long){
        for (i in timeIter){
            val dtid = i+dateValue.shl(4)
            pillCheckDao.deleteAllPillChecksByDtid(dtid)
            dateTimeDao.deleteDateTimeById(dtid)
        }
    }
    suspend fun delete7AgoPillChecks(){
        val datetime = LocalDateTime.now()
        val dateValueNow = DateTimeManager().getDateValue(datetime)
        val dateValue = dateValueNow - 7

        for (i in timeIter){
            val dtid = i+dateValue.shl(4)
            pillCheckDao.deleteAllPillChecksByDtid(dtid)
            dateTimeDao.deleteDateTimeById(dtid)
        }
    }

    suspend fun updatePillCheck(pid: Long, dtid: Long, checked: Boolean){
        println("DEBUG1")
        val pillCheck = pillCheckDao.getPillCheckByPidAndDtid(pid, dtid)
        println("DEBUG2 $pillCheck")
        pillCheck.checked = checked
        pillCheckDao.updatePillCheck(pillCheck)
        println("DEBUG3 ")

        val dateTime = dateTimeDao.getDateTimeById(dtid)
        if (dateTime != null) {
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

}