package com.jfpsolucoes.unipplus2.modules.signin.data

import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UPSignInService {
    @Headers("Content-Type: application/json")
    @POST(BuildConfig.API_LOCATION + "/auth/v1/sign-in")
    suspend fun postSignIn(@Body data: Any): Response<SignInResponse>

}