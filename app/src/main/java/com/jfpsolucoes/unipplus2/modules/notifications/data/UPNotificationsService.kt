package com.jfpsolucoes.unipplus2.modules.notifications.data

import com.jfpsolucoes.unipplus2.modules.notifications.domain.models.UPNotification
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UPNotificationsService {

    @Headers("Cache-Control: max-stale=600")
    @GET("sec/v1/notifications")
    suspend fun getNotifications(): Response<List<UPNotification>>

}