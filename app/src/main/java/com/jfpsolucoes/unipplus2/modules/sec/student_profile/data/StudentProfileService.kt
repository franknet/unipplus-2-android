package com.jfpsolucoes.unipplus2.modules.sec.student_profile.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface StudentProfileService {

    @Headers("Cache-Control: max-age=600")
    @GET("student/profile")
    fun getStudentProfile(): Response<String>

}
