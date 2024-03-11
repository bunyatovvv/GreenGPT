package com.example.greengpt.util

import java.text.SimpleDateFormat
import java.util.Date

object Time {

    fun timeStamp() : String {
        val timeStamp = java.sql.Timestamp(System.currentTimeMillis())
        val sdf = SimpleDateFormat("HH:mm")
        val time = sdf.format(Date(timeStamp.time))
        return time.toString()
    }
}