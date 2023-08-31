package com.example.pill_checker.repo

import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.dao.timeIter
import com.example.pill_checker.data.Time

class TimeRepo(private val database: MainDatabase) {
    private val timeDao = database.timeDao()

    suspend fun initialTime() {
        //기존에 있는지 확인 후 없다면 생성
        for (bit in timeIter) {
            val time = Time(tid = bit, count = 0)
            timeDao.insertTime(time)
        }
    }

    suspend fun isTimeAnyPill(tid: Int): Boolean {
        val time = timeDao.getTimeById(tid)
        return time.count > 0
    }
//    suspend fun veryNextDtid(dtid: Long): Long?{
//        var timeValue = dtid.and(0b1111).toInt()
//        val pillExist: List<Boolean> = isTimesAnyPill()
//        var panelDateValue = dtid.shr(4)
//
//        var existPill = false
//        for (timeExist in pillExist){
//            if (timeExist){
//                existPill = true
//                break
//            }
//        }
//        if(!existPill){
//            return null
//        }
//
//        while (!pillExist[timeIter.indexOf(timeValue)]){
//            timeValue = timeValue.shl(1)
//            //뒤에 남은 약 일정이 없는 상황임을 인지
//            if(timeValue > 0b1000) {
//                timeValue = getLastTimeValue(pillExist) ?: return null
//                break
//            }
//        }
//        return panelDateValue.shl(4).or(timeValue.toLong())
//    }

    suspend fun lastTid(tid: Int): Int? {
        var timeValue = tid

        var existPill = false
        for (i in 0..3) {
            if (isTimeAnyPill(timeValue)) {
                existPill = true
                break
            }
            timeValue = timeValue.shr(1)
            if(timeValue == 0){
                timeValue = 0b1000
            }
        }
        if (!existPill) {
            return null
        }

        return timeValue
    }

    suspend fun pastLastTid(tid: Int): Int?{
        var past = tid.shr(1)
        if(past == 0) past = 0b1000
        return lastTid(past)
    }

    private fun getLastTimeValue(existPill: List<Boolean>): Int? {
        for (time in timeIter.reversed()) {
            if (existPill[timeIter.indexOf(time)]) {
                return time
            }
        }
        return null
    }
}