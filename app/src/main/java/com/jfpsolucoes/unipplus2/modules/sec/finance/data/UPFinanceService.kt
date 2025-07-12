package com.jfpsolucoes.unipplus2.modules.sec.finance.data

import com.jfpsolucoes.unipplus2.modules.sec.finance.domain.model.UPFinanceBillsResponse
import com.jfpsolucoes.unipplus2.modules.sec.finance.domain.model.UPFinanceExtractResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UPFinanceService {

    @Headers("Cache-Control: max-stale=600")
    @GET("sec/finance/v1/extract")
    suspend fun getExtract(): Response<UPFinanceExtractResponse>

    @Headers("Cache-Control: max-stale=600")
    @GET("sec/finance/v1/bills")
    suspend fun getBills(): Response<UPFinanceBillsResponse>
}