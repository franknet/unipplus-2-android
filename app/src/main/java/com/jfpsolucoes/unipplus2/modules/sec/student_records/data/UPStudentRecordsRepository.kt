package com.jfpsolucoes.unipplus2.modules.sec.student_records.data

import com.jfpsolucoes.unipplus2.core.networking.HttpService

class UPStudentRecordsRepository(
    private val service: UPStudentRecordsService = HttpService.create(UPStudentRecordsService::class.java)
) {
    suspend fun getStudentRecords() = service.getStudentRecords()
    suspend fun getDisciplineTestHistogram(path: String) = service.getDisciplineTestHistogram(path)

}