package com.jfpsolucoes.unipplus2.modules.signin.domain.repository

import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.modules.signin.data.SignInService
import com.jfpsolucoes.unipplus2.modules.signin.data.UPSingInRepository

class UPSignInRepositoryImpl(
    private val service: SignInService = HttpService.create(SignInService::class.java)
): UPSingInRepository {
    override suspend fun signIn(data: Any): String {
        val response = service.postSignIn(data)
        if (response.code() != 200) throw Exception()
        val responseBody = response.body() ?: throw Exception()
        return responseBody
    }

    override suspend fun getSession(): String {
        val response = service.getSession()
        if (response.code() != 200) throw Exception()
        val responseBody = response.body() ?: throw Exception()
        return responseBody
    }

}