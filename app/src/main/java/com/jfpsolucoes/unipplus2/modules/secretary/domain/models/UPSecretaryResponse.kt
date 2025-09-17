package com.jfpsolucoes.unipplus2.modules.secretary.domain.models

data class UPSecretaryResponse(
    val features: List<UPSecretaryFeature>?
)

data class UPSecretaryFeature(
    val description: String?,
    val deeplink: String?,
    val portalUrl: String?,
    val iconName: String?
)
