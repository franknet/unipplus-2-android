package com.jfpsolucoes.unipplus2.modules.signin.data

import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity

interface UPSingInRepository {
    suspend fun signIn(data: Any): UPUserProfileEntity
}