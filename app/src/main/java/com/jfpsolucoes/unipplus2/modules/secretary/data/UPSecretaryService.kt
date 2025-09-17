package com.jfpsolucoes.unipplus2.modules.secretary.data

import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.core.networking.UPApiEndpoints
import com.jfpsolucoes.unipplus2.core.networking.UPHttpHeaders
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UPSecretaryService {
    @Headers(UPHttpHeaders.CACHE_CONTROL_MAX_STALE_3600)
    @GET(UPApiEndpoints.Secretary.FEATURES)
    suspend fun getSecretaryFeatures(): Response<UPSecretaryResponse>
}