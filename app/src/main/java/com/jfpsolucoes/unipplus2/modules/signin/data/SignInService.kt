package com.jfpsolucoes.unipplus2.modules.signin.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignInService {

    @Headers("Content-Type: application/json")
    @POST("v1/sign_in")
    suspend fun postSignIn(@Body data: Any): Response<String>

    @GET("sec/v1/session")
    suspend fun getSession(): Response<String>

}