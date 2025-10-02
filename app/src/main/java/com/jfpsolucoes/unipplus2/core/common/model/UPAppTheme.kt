package com.jfpsolucoes.unipplus2.core.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class UPAppTheme {
    @SerializedName("theme_dark")
    DARK,
    @SerializedName("theme_light")
    LIGHT,
    @SerializedName("theme_system")
    SYSTEM
}