package com.jfpsolucoes.unipplus2.core.common.model

import kotlinx.serialization.Serializable

@Serializable
data class UPNameValue(
    val name: String?,
    val value: String?
)
