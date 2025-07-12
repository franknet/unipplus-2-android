package com.jfpsolucoes.unipplus2.modules.home.domain.models

import com.google.gson.annotations.SerializedName

enum class UPSystemsType {
    @SerializedName("web")
    WEB,
    @SerializedName("pdf")
    PDF,
    @SerializedName("home_dashboard")
    HOME_DASHBOARD,
}

data class UPSystemNavigation(
    val description: String?,
    val type: UPSystemsType?
)

data class UPSystem(
    val description: String?,
    val navigation: UPSystemNavigation?,
    val url: String?,
    val icon: String?
)