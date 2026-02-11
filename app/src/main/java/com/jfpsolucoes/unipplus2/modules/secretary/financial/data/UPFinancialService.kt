package com.jfpsolucoes.unipplus2.modules.secretary.financial.data

import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import com.jfpsolucoes.unipplus2.core.networking.UPHttpHeaders
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialDebtsData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialExtractData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialFeaturesData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UPFinancialService {
    @Headers(UPHttpHeaders.CACHE_CONTROL_MAX_STALE_3600)
    @GET(UPApiEndpoints.Secretary.Financial.FEATURES)
    suspend fun getFeatures(): Response<UPFinancialFeaturesData>

    @Headers(UPHttpHeaders.CACHE_CONTROL_MAX_STALE_3600)
    @GET(UPApiEndpoints.Secretary.Financial.EXTRACT)
    suspend fun getExtract(@Query("year") period: String?): Response<UPFinancialExtractData>

    @Headers(UPHttpHeaders.CACHE_CONTROL_MAX_STALE_3600)
    @GET(UPApiEndpoints.Secretary.Financial.DEBTS)
    suspend fun getDebts(): Response<UPFinancialDebtsData>

    @GET("{path}")
    suspend fun download(@Path("path") path: String): Response<ResponseBody>

}