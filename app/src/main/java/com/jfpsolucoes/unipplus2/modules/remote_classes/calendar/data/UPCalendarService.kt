package com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.data

import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.models.UPCalendarData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface UPCalendarService {
    @GET(UPApiEndpoints.RemoteClasses.REMOTE_CLASSES_API)
    suspend fun getCalendar(
        @Query("format") format: String?,
    ): Response<UPCalendarData>

    @GET("{path}")
    suspend fun updateCalendar(
        @Path("path", encoded = true) path: String,
        @QueryMap query: Map<String, String?>
    ): Response<UPCalendarData>
}