package com.jfpsolucoes.unipplus2.modules.secretary.financial.data

import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import com.jfpsolucoes.unipplus2.core.networking.UPHttpHeaders
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialDebtsData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialExtractData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialFeaturesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UPFinancialService {
    @Headers(UPHttpHeaders.CACHE_CONTROL_MAX_STALE_3600)
    @GET(UPApiEndpoints.Secretary.Financial.FEATURES)
    suspend fun getFeatures(): Response<UPFinancialFeaturesData>

    @Headers(UPHttpHeaders.CACHE_CONTROL_MAX_STALE_3600)
    @GET(UPApiEndpoints.Secretary.Financial.EXTRACT)
    suspend fun getExtract(): Response<UPFinancialExtractData>

    @Headers(UPHttpHeaders.CACHE_CONTROL_MAX_STALE_3600)
    @GET(UPApiEndpoints.Secretary.Financial.DEBTS)
    suspend fun getDebts(): Response<UPFinancialDebtsData>
}