package com.example.pill_checker.repo

import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.dao.timeIter
import com.example.pill_checker.data.Time

class TimeRepo(private val database: MainDatabase.MainDatabase){
    private val timeDao = database.timeDao()

    fun initialTime(){
        for (bit in timeIter){
            val time = Time(tid = bit, count = 0)
            timeDao.insertTime(time)
        }
    }

    fun isTimesAnyPill(): List<Boolean>{
        val anyPill = mutableListOf<Boolean>()

        for (time in timeIter){
            val time = timeDao.getTimeById(time)
            anyPill.add(time.count > 0)
        }

        return anyPill
    }
    fun veryNextDtid(dtid: Long): Long?{
        var timeValue = dtid.and(0b1111).toInt()
        val pillExist: List<Boolean> = isTimesAnyPill()
        var panelDateValue = dtid.shr(4)

        var existPill = false
        for (timeExist in pillExist){
            if (timeExist){
                existPill = true
                break
            }
        }
        if(!existPill){
            return null
        }

        while (!pillExist[timeIter.indexOf(timeValue)]){
            timeValue = timeValue.shl(1)
            //뒤에 남은 약 일정이 없는 상황임을 인지
            if(timeValue > 0b1000) {
                timeValue = getLastTimeValue(pillExist) ?: return null
                break
            }
        }
        return panelDateValue.shl(4).or(timeValue.toLong())
    }

    private fun getLastTimeValue(existPill: List<Boolean>): Int?{
        for(time in timeIter.reversed()){
            if(existPill[timeIter.indexOf(time)]){
                return time
            }
        }
        return null
    }
}