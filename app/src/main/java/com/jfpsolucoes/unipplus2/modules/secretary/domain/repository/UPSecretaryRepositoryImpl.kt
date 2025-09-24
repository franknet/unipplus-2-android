package com.jfpsolucoes.unipplus2.modules.secretary.domain.repository

import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.modules.secretary.data.UPSecretaryRepository
import com.jfpsolucoes.unipplus2.modules.secretary.data.UPSecretaryService
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeaturesResponse

class UPSecretaryRepositoryImpl(
    val service: UPSecretaryService = HttpService.create(UPSecretaryService::class.java)
): UPSecretaryRepository {
    override suspend fun getSecretaryFeatures(): UPSecretaryFeaturesResponse {
        val response = service.getSecretaryFeatures()
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }
}