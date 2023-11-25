package com.tydev.millietest.util

import java.text.SimpleDateFormat
import java.util.Date

fun convertToSimpleFormat(dateStr: String): String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val targetFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date: Date = originalFormat.parse(dateStr)
    return targetFormat.format(date)
}