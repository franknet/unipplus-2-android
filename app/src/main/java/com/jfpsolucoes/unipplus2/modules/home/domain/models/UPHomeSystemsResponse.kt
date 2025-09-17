package com.jfpsolucoes.unipplus2.modules.home.domain.models

data class UPHomeSystemsResponse(
    val feature: List<UPSystem>?,
    val web: List<UPSystem>?
)

object UPSystemType {
    const val WEB = "web"
    const val FEATURE = "feature"
}

object UPSystemDeeplink {
    const val SECRETARY = "/secretary"
}

data class UPSystem(
    val id: Int? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    val deeplink: String? = null,
    val type: String? = null,
    val url: String? = null,
)
