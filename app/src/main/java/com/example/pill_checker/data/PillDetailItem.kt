package com.example.pill_checker.data

data class PillDetailItem(
    val pid: Int,
    val name: String,
    val imageId: Int,
    val times: List<String>,
    val pillHalfNum: Int
    )
