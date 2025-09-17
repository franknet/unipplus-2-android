package com.jfpsolucoes.unipplus2.modules.signin.domain.models

data class SignInResponse(
    val academic: Academic?,
    val user: User?
)

data class Academic(
    val campus: Campus?,
    val course: Course?,
    val institution: String?
)

data class User(
    val email: String?,
    val gender: String?,
    val id: Any?,
    val name: String?,
    val photo: String?,
    val rg: String?
)

data class Campus(
    val code: String?,
    val name: String?
)

data class Course(
    val code: String?,
    val curriculum: String?,
    val educationLevel: Int?,
    val mainClass: String?,
    val name: String?,
    val shift: String?
)