package com.jfpsolucoes.unipplus2.modules.secretary.data

import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeaturesResponse

interface UPSecretaryRepository {
    suspend fun getSecretaryFeatures(): UPSecretaryFeaturesResponse
}