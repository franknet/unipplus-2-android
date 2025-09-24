package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain

import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.data.UPStudentRecordsRepository
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.data.UPStudentRecordsService
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models.UPStudentRecordsDisciplinesResponse

class UPStudentRecordsRepositoryImpl(
    val service: UPStudentRecordsService = HttpService.create(UPStudentRecordsService::class.java)
): UPStudentRecordsRepository {
    override suspend fun getDisciplines(): UPStudentRecordsDisciplinesResponse {
        val response = service.getDisciplinesGroup()
        if (!response.isSuccessful) { throw Exception(response.errorBody()?.string()) }
        return response.body() ?: throw Exception("Dados não encontrados")
    }
}