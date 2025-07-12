package com.jfpsolucoes.unipplus2.modules.studentprofile.data

import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPStudentProfile
import retrofit2.Response
import retrofit2.http.GET

interface UPStudentProfileService {
    @GET("v1/student_profile")
    suspend fun getStudentProfile(): Response<UPStudentProfile>
}