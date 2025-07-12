package com.jfpsolucoes.unipplus2.modules.sec.academic_records.data

import com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain.models.UPAcademicRecordsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UPAcademicRecordsService {

    @Headers("Cache-Control: max-stale=600")
    @GET("sec/v1/academic_records")
    suspend fun getAcademicRecords(): Response<UPAcademicRecordsResponse>

}