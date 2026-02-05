package com.jfpsolucoes.unipplus2.ui.components.web

import kotlinx.serialization.Serializable
import java.net.URI

@Serializable
data class PortalWebViewSettings(
    val url: String,
)

fun PortalWebViewSettings.isSPA(): Boolean {
    val uri = URI(url)
    val fragment = uri.fragment
    return fragment != null
}