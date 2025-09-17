package com.jfpsolucoes.unipplus2.modules.home.domain

import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.modules.home.data.UPHomeRepository
import com.jfpsolucoes.unipplus2.modules.home.data.UPHomeService
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse

class UPHomeRepositoryImpl(
    private val service: UPHomeService = HttpService.create(UPHomeService::class.java)
): UPHomeRepository {
    override suspend fun getSystems(): UPHomeSystemsResponse {
        val response = service.getSystems()
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }
}