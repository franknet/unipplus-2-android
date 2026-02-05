package com.jfpsolucoes.unipplus2.modules.secretary.features.data

import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import com.jfpsolucoes.unipplus2.core.networking.UPHttpHeaders
import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.models.UPSecretaryFeaturesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UPSecretaryService {
    @GET(UPApiEndpoints.Secretary.FEATURES)
    suspend fun getSecretaryFeatures(): Response<UPSecretaryFeaturesResponse>
}