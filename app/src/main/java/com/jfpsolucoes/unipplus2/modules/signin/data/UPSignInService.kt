package com.jfpsolucoes.unipplus2.modules.signin.data

import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import com.jfpsolucoes.unipplus2.core.networking.UPHttpHeaders
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UPSignInService {
    @Headers(UPHttpHeaders.CONTENT_TYPE_APPLICATION_JSON)
    @POST(UPApiEndpoints.Auth.SIGN_IN)
    suspend fun postSignIn(@Body data: Any): Response<UPSignInResponse>

}