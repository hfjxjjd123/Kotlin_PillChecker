package com.example.pill_checker.repo

import com.example.pill_checker.dao.DateTimeDao
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

    private suspend fun pushPillCheck(pushed: PillCheck){
        val pillCheck: PillCheck? = getPillCheckByPidAndDtid(pushed.pid, pushed.dtid)
        if(pillCheck != null){
            pillCheckDao.updatePillCheck(pushed)
        }else{
            pillCheckDao.insertPillCheck(pushed)
        }
    }

    private suspend fun pushDateTime(pushed: DateTime){
        val dateTime: DateTime? = dateTimeDao.getDateTimeById(pushed.dtid)
        if(dateTime != null){
            dateTimeDao.updateDateTime(pushed)
        }else{
            dateTimeDao.insertDateTime(pushed)
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

    suspend fun getPillLightsByTid(tid: Int): List<PillLight> = pillLightDao.getPillLightsByTid(tid)

    suspend fun updatePillLight(pid: Long, tid: Int, checked: Boolean){
        val pillLight = pillLightDao.getPillLightByPid(pid, tid)
        pillLightDao.updatePillLight(pillLight.copy(checked = checked))
    }

    suspend fun pillLightToPillChecked(pillLights: List<PillLight>){
        val dtid = DateTimeManager.getDateTimeValueWhenEnd()

        //비어있다면 PillLight 생성하지 않을 것.
        if(pillLights.isEmpty()) return

        val dateTime = DateTime(dtid = dtid, checked = true)
        pushDateTime(dateTime)

        for (pillLight in pillLights){
            val pillCheck = PillCheck(pid = pillLight.pid, name = pillLight.name, dtid = dtid, checked = pillLight.checked)
            pushPillCheck(pillCheck)
            if(!pillCheck.checked){
                dateTime.checked = false
            }
            pillLightDao.updatePillLight(pillLight.copy(checked = false))
        }
        dateTimeDao.updateDateTime(dateTime)
    }

}