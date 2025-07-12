package com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.data.UPAcademicRecordsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPGetAcademicRecordsUseCase(
    private val repository: UPAcademicRecordsRepository = UPAcademicRecordsRepository(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    operator fun invoke() = flow {
        val response = withContext(dispatcher) {
            repository.getAcademicRecords()
        }
        val body = response.body()
        emit(body)
    }.toUIStateFlow()

}