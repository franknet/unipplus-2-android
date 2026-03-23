package com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.models

data class UPCalendarData(
    val availableMonths: List<UPCalendarAvailableMonth>,
    val calendarMenu: List<UPCalendarMenu>,
    val monthYear: String,
    val weekDays: List<UPCalendarWeekDay>
)

const val UP_CALENDAR_FORMAT_FULL = "full"
const val UP_CALENDAR_FORMAT_LINEAR = "linear"

data class UPCalendarAvailableMonth(
    val label: String,
    val selected: Boolean,
    val value: String
)

data class UPCalendarMenu(
    val label: String,
    val path: String
)

data class UPCalendarWeekDay(
    val days: List<UPCalendarDay>,
    val label: String
)

data class UPCalendarDay(
    val backgroundColor: Long,
    val date: String,
    val day: Int,
    val description: String,
    val events: List<UPCalendarEvent>,
    val isToday: Boolean
)

data class UPCalendarEvent(
    val description: String
)