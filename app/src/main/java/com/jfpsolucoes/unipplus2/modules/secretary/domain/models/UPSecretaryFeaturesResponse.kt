package com.jfpsolucoes.unipplus2.modules.secretary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UPSecretaryFeaturesResponse(
    val features: List<UPSecretaryFeature>?
)

@Parcelize
data class UPSecretaryFeature(
    val description: String?,
    val deeplink: String?,
    val portalUrl: String?,
    val iconSVG: String?,
    val enabled: Boolean = false,
    val message: String?
) : Parcelable
