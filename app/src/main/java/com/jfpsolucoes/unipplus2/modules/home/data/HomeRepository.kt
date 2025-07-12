package com.jfpsolucoes.unipplus2.modules.home.data

import com.jfpsolucoes.unipplus2.core.networking.HttpService

class HomeRepository(
    private val service: HomeService = HttpService.create(HomeService::class.java)
) {
    suspend fun getSystems() = service.getSystems()
}