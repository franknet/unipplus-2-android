package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.data

import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models.UPStudentRecordsDisciplinesResponse
import retrofit2.Response
import retrofit2.http.GET

interface UPStudentRecordsService {
    @GET(UPApiEndpoints.Secretary.StudentRecords.DISCIPLINES)
    suspend fun getDisciplinesGroup(): Response<UPStudentRecordsDisciplinesResponse>
}