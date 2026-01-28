package com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UPFinancialFeaturesData(
    val features: List<UPFinancialFeature>? = null,
    val periods: List<String>? = null
): Parcelable

@Parcelize
data class UPFinancialFeature(
    val deepLink: String? = null,
    val enabled: Boolean? = null,
    val title: String? = null
): Parcelable
