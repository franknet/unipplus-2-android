package com.jfpsolucoes.unipplus2.modules.home.data

import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import retrofit2.Response
import retrofit2.http.GET

interface UPHomeService {
    @GET(UPApiEndpoints.Auth.SYSTEMS)
    suspend fun getSystems(): Response<UPHomeSystemsResponse>

}