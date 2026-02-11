package com.jfpsolucoes.unipplus2.core.database.entities

data class UPUserProfileEntity(
    val rg: String? = null,
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val photo: String? = null,
    val academic: UPUserAcademicEntity? = null,
    val preferences: UPUserPreferencesEntity? = null
)