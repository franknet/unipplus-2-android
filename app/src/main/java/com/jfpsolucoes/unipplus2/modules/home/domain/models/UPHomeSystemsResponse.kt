package com.jfpsolucoes.unipplus2.modules.home.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class UPHomeSystemsResponse(
    val feature: List<UPSystem>? = null,
    val web: List<UPSystem>? = null
): Parcelable

object UPSystemType {
    const val WEB = "web"
    const val FEATURE = "feature"
}

@Serializable
enum class UPSystemDeeplink {
    @SerializedName("/secretary")
    SECRETARY,
    @SerializedName("/remote-classes")
    REMOTE_CLASSES,
    @SerializedName("/settings")
    SETTINGS,
    @SerializedName("/subscription")
    SUBSCRIPTION
}


@Parcelize
data class UPSystem(
    val id: Int? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    val deeplink: UPSystemDeeplink? = null,
    val type: String? = null,
    val url: String? = null,
    val isEnabled: Boolean = false,
    val message: String? = null,
    val iconSVG: String? = null
): Parcelable
