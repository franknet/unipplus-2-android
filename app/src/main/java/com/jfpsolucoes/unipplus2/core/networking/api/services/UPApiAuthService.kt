package com.jfpsolucoes.unipplus2.core.networking.api.services

import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPCredentials
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface UPApiAuthService {
    @POST(UPApiEndpoints.Auth.SIGN_IN)
    suspend fun postSignIn(credentials: UPCredentials): Response<String>
    @GET(UPApiEndpoints.Auth.SYSTEMS)
    suspend fun getSystems(): Response<String>
}