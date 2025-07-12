package com.jfpsolucoes.unipplus2.modules.home.data

import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {
    @GET("v1/systems")
    suspend fun getSystems(): Response<List<UPSystem>>
}