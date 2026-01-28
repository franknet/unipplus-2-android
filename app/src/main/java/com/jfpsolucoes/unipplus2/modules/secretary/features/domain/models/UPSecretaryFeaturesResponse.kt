package com.jfpsolucoes.unipplus2.modules.secretary.features.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UPSecretaryFeaturesResponse(
    val features: List<UPSecretaryFeature>?
)

const val SECRETARY_STUDENT_RECORDS_DEEPLINK = "/secretary/student-records"
const val SECRETARY_FINANCIAL_DEEPLINK = "/secretary/financial"

@Parcelize
data class UPSecretaryFeature(
    val description: String?,
    val deeplink: String?,
    val portalUrl: String?,
    val iconSVG: String?,
    val enabled: Boolean = false,
    val message: String?,
) : Parcelable
