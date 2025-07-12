package com.jfpsolucoes.unipplus2.modules.studentprofile.data

import com.jfpsolucoes.unipplus2.core.networking.HttpService

class UPStudentProfileRepository(
    private val service: UPStudentProfileService = HttpService.create(UPStudentProfileService::class.java)
) {
    suspend fun getStudentProfile() = service.getStudentProfile()
}