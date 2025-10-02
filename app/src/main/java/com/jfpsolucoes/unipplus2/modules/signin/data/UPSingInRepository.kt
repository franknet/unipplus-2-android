package com.jfpsolucoes.unipplus2.modules.signin.data

import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInResponse

interface UPSingInRepository {
    suspend fun signIn(data: Any): UPSignInResponse
}