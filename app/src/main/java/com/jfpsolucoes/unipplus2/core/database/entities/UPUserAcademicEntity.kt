package com.jfpsolucoes.unipplus2.core.database.entities

data class UPUserAcademicEntity(
    val institution: String? = null,
    val campus: UPUserAcademicCampusEntity? = null,
    val course: UPUserAcademicCourseEntity? = null
)
