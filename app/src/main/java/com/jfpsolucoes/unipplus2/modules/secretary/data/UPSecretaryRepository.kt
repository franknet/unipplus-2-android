package com.jfpsolucoes.unipplus2.modules.secretary.data

import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryResponse

interface UPSecretaryRepository {
    suspend fun getSecretaryFeatures(): UPSecretaryResponse
}