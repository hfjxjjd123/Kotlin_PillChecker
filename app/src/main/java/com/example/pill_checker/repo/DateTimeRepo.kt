package com.example.pill_checker.repo

import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.dao.timeIter
import com.example.pill_checker.data.DateTime

class DateTimeRepo(private val database: MainDatabase.MainDatabase) {
    private val dateTimeDao = database.dateTimeDao()

    fun getAllDateTime() = dateTimeDao.getAllDateTimes()

    fun getAllDateTimes(dateNow: Long): Pair<List<List<DateTime?>>, List<Boolean>> {
        val itemList = mutableListOf<MutableList<DateTime?>>()

        for (date in dateNow - 5..dateNow) {
            itemList.add(0, getDateTimeByDate(date))
        }

        val categoryList = categoryIs(itemList)
        for (indexCategory in categoryList.indices) {
            if (!categoryList[indexCategory]) {
                for (indexDate in itemList.indices) {
                    itemList[indexDate].removeAt(indexCategory)
                }
            }
        }


        return Pair(itemList, categoryList)
    }

    private fun getDateTimeByDate(date: Long): MutableList<DateTime?> {
        val dateTimeList = mutableListOf<DateTime?>()

        for (time in timeIter) {
            val dtid = date + time
            val dateTime = dateTimeDao.getDateTimeById(dtid)
            dateTimeList.add(dateTime)
        }

        return dateTimeList
    }

    private fun categoryIs(itemList: List<List<DateTime?>>): List<Boolean> {
        val categoryList = mutableListOf<Boolean>()

        var haveMorning = false
        var haveLunch = false
        var haveDinner = false
        var haveSleep = false

        for (item in itemList) {
            if (item[0] != null) {
                haveMorning = true
                break
            }
        }
        for (item in itemList) {
            if (item[1] != null) {
                haveLunch = true
                break
            }
        }
        for (item in itemList) {
            if (item[2] != null) {
                haveDinner = true
                break
            }
        }
        for (item in itemList) {
            if (item[3] != null) {
                haveSleep = true
                break
            }
        }

        categoryList.add(haveMorning)
        categoryList.add(haveLunch)
        categoryList.add(haveDinner)
        categoryList.add(haveSleep)

        return categoryList

    }

}