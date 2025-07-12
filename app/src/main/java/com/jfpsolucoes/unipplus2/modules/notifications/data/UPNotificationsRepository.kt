package com.jfpsolucoes.unipplus2.modules.notifications.data

import com.jfpsolucoes.unipplus2.core.networking.HttpService

class UPNotificationsRepository(
    private val service: UPNotificationsService = HttpService.create(UPNotificationsService::class.java)
) {
    suspend fun getNotifications() = service.getNotifications()
}