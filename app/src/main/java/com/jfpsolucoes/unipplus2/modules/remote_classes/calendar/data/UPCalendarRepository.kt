package com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.data

import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.models.UPCalendarData

interface UPCalendarRepository {
    suspend fun getCalendar(format: String?): UPCalendarData
    suspend fun updateCalendar(path: String, query: Map<String, String?>): UPCalendarData
}