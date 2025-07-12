package com.jfpsolucoes.unipplus2.core.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("v1/sign_in")
    fun postSignIn(@Body data: Any): Call<String>

    @GET("v1/student_profile")
    fun getStudentProfile(): Call<String>

    @GET("v1/student_records")
    fun getStudentRecords(): Call<String>

    @GET("v1/academic_records")
    fun getAcademicRecords(): Call<String>

    interface Finance {
        @GET("v1/finance/extract")
        fun getExtract(): Call<String>

        @GET("v1/finance/bank_bills")
        fun getBankBills(): Call<String>
    }
}