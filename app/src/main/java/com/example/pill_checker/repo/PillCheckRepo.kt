package com.example.pill_checker.repo

import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.dao.timeIter
import com.example.pill_checker.data.DateTime
import com.example.pill_checker.data.PillCheck
import com.example.pill_checker.data.PillLight
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
        val dateValueNow = DateTimeManager.getDateValue(datetime)
        val dateValue = dateValueNow - 7

        for (i in timeIter){
            val dtid = i+dateValue.shl(4)
            pillCheckDao.deleteAllPillChecksByDtid(dtid)
            dateTimeDao.deleteDateTimeById(dtid)
        }
    }

    suspend fun updatePillCheck(pid: Long, dtid: Long, checked: Boolean){
        val pillCheck = pillCheckDao.getPillCheckByPidAndDtid(pid, dtid)
        pillCheck.checked = checked
        pillCheckDao.updatePillCheck(pillCheck)

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


    /////////////////

    suspend fun getPillLightsByDtid(dtid: Long): List<PillLight> = pillLightDao.getPillLightsByDtid(dtid)

    suspend fun updatePillLight(pid: Long, tid: Int, name: String){
        val pillLight = pillLightDao.getPillLightByPid(pid)
        pillLight.tid = tid
        pillLight.name = name
        pillLightDao.updatePillLight(pillLight)
    }

    //TODO 패널에 띄우기 전에 즉, time-end시 다음 타임에 대해서 이렇게 적용해보는게 좋을듯
    suspend fun rollbackPillLight(tid: Int){
        val pillLights: List<PillLight> = pillLightDao.getPillLightsByTid(tid)
        for (pillLight in pillLights){
            pillLight.checked = false
            pillLightDao.updatePillLight(pillLight)
        }
    }

}