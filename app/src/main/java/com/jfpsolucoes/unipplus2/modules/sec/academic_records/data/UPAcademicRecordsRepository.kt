package com.jfpsolucoes.unipplus2.modules.sec.academic_records.data

import com.jfpsolucoes.unipplus2.core.networking.HttpService

class UPAcademicRecordsRepository(
    val service: UPAcademicRecordsService = HttpService.create(UPAcademicRecordsService::class.java)
) {
    suspend fun getAcademicRecords() = service.getAcademicRecords()
}