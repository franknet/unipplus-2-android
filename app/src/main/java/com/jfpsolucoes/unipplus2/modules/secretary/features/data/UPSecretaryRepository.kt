package com.jfpsolucoes.unipplus2.modules.secretary.features.data

import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.models.UPSecretaryFeaturesResponse

interface UPSecretaryRepository {
    suspend fun getSecretaryFeatures(): UPSecretaryFeaturesResponse
}