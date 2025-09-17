package com.jfpsolucoes.unipplus2.modules.home.data

import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.core.networking.UPHttpHeaders
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface UPHomeService {
    @Headers(UPHttpHeaders.CACHE_CONTROL_MAX_STALE_3600)
    @GET(BuildConfig.API_LOCATION + "/auth/v1/systems")
    suspend fun getSystems(): Response<UPHomeSystemsResponse>

}