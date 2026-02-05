package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.data

import com.jfpsolucoes.unipplus2.core.networking.UPHttpHeaders
import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models.UPStudentRecordsDisciplinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UPStudentRecordsService {
    @Headers(UPHttpHeaders.CACHE_CONTROL_MAX_STALE_3600)
    @GET(UPApiEndpoints.Secretary.StudentRecords.DISCIPLINES)
    suspend fun getDisciplinesGroup(): Response<UPStudentRecordsDisciplinesResponse>
}