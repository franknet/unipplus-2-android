package com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.repository

import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.modules.secretary.features.data.UPSecretaryRepository
import com.jfpsolucoes.unipplus2.modules.secretary.features.data.UPSecretaryService
import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.models.UPSecretaryFeaturesResponse
import com.jfpsolucoes.unipplus2.modules.secretary.financial.data.UPFinancialRepository
import com.jfpsolucoes.unipplus2.modules.secretary.financial.data.UPFinancialService
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialDebtsData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialExtractData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialFeaturesData

class UPFinancialRepositoryImpl(
    val service: UPFinancialService = HttpService.create(UPFinancialService::class.java)
): UPFinancialRepository {
    override suspend fun getFeatures(): UPFinancialFeaturesData {
        val response = service.getFeatures()
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }

    override suspend fun getExtract(): UPFinancialExtractData {
        val response = service.getExtract()
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }

    override suspend fun getDebts(): UPFinancialDebtsData {
        val response = service.getDebts()
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }
}