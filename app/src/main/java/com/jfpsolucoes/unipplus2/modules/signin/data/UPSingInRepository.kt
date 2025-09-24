package com.jfpsolucoes.unipplus2.modules.signin.data

interface UPSingInRepository {
    suspend fun signIn(data: Any): String
}