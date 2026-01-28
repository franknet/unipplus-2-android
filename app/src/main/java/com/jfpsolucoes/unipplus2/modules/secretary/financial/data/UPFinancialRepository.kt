package com.jfpsolucoes.unipplus2.modules.secretary.financial.data

import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialDebtsData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialExtractData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialFeaturesData

interface UPFinancialRepository {
    suspend fun getFeatures(): UPFinancialFeaturesData
    suspend fun getExtract(): UPFinancialExtractData
    suspend fun getDebts(): UPFinancialDebtsData
}