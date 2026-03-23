package com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.repository

import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.data.UPCalendarRepository
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.data.UPCalendarService
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.models.UPCalendarData

class UPCalendarRepositoryImpl(
    private val service: UPCalendarService = HttpService.create(UPCalendarService::class.java)
): UPCalendarRepository {

    override suspend fun getCalendar(format: String?): UPCalendarData {
        val response = service.getCalendar(format)
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }

    override suspend fun updateCalendar(
        path: String,
        query: Map<String, String?>
    ): UPCalendarData {
        val response = service.updateCalendar(path, query)
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }
}