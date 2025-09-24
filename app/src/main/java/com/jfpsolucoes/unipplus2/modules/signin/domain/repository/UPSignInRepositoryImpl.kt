package com.jfpsolucoes.unipplus2.modules.signin.domain.repository

import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.modules.signin.data.UPSignInService
import com.jfpsolucoes.unipplus2.modules.signin.data.UPSingInRepository

class UPSignInRepositoryImpl(
    private val service: UPSignInService = HttpService.create(UPSignInService::class.java)
): UPSingInRepository {
    override suspend fun signIn(data: Any): String {
        val response = service.postSignIn(data)
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }

}