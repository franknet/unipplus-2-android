package com.jfpsolucoes.unipplus2.modules.sec.student_records.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.sec.student_records.data.UPStudentRecordsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPGetStudentRecordsUseCase(
    private val repository: UPStudentRecordsRepository = UPStudentRecordsRepository(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        val response = withContext(dispatcher) {
            repository.getStudentRecords()
        }
        emit(response.body())
    }.toUIStateFlow()

}