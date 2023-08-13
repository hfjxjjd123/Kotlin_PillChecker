package com.example.pill_checker.data

data class PillDone(
    val pid: Int,
    val name: String,
    val date: Int,
    val time: String,
    var done: String
    )
