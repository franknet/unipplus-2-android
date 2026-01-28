package com.jfpsolucoes.unipplus2.modules.secretary.features.domain.repository

import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.modules.secretary.features.data.UPSecretaryRepository
import com.jfpsolucoes.unipplus2.modules.secretary.features.data.UPSecretaryService
import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.models.UPSecretaryFeaturesResponse

class UPSecretaryRepositoryImpl(
    val service: UPSecretaryService = HttpService.create(UPSecretaryService::class.java)
): UPSecretaryRepository {
    override suspend fun getSecretaryFeatures(): UPSecretaryFeaturesResponse {
        val response = service.getSecretaryFeatures()
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }
}