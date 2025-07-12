package com.jfpsolucoes.unipplus2.modules.sec.student_records.data

import com.jfpsolucoes.unipplus2.modules.sec.student_records.domain.model.UPDisciplineTestHistogram
import com.jfpsolucoes.unipplus2.modules.sec.student_records.domain.model.UPStudentRecordsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UPStudentRecordsService {

//    @Headers("Cache-Control: max-stale=600")
    @GET("sec/v1/student_records")
    suspend fun getStudentRecords(): Response<UPStudentRecordsResponse>

    @GET("{url}")
    suspend fun getDisciplineTestHistogram(@Path(value = "url") path: String): Response<UPDisciplineTestHistogram>
}