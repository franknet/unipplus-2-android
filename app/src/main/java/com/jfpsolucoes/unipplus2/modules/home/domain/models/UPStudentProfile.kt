package com.jfpsolucoes.unipplus2.modules.home.domain.models

import com.jfpsolucoes.unipplus2.core.common.model.UPNameValue
import kotlinx.serialization.Serializable

@Serializable
data class UPCourse(
    val id: String?,
    val name: String?,
    val info: List<@Serializable UPNameValue>?
)

@Serializable
data class UPStudentProfile(
    val rg: String?,
    val name: String?,
    val info: List<@Serializable UPNameValue>?,
    val course: UPCourse?
)
