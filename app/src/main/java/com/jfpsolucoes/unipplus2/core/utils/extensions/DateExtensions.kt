package com.jfpsolucoes.unipplus2.core.utils.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


fun Calendar.resetTime(): Calendar {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
    return this
}

fun Calendar.setDay(value: Int): Calendar {
    this.set(Calendar.DAY_OF_MONTH, value)
    return this
}

@SuppressLint("SimpleDateFormat")
fun Date.format(pattern: String): String = SimpleDateFormat(pattern).format(this)

@SuppressLint("SimpleDateFormat")
fun Date.from(source: String, pattern: String): Date = SimpleDateFormat(pattern).parse(source) ?: this

fun Date.equalsTo(other: Date): Boolean = this.compareTo(other) == 0