package com.jfpsolucoes.unipplus2.modules.sec.student_records.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.sec.student_records.data.UPStudentRecordsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPGetDisciplineTestHistogramUseCase(
    private val repository: UPStudentRecordsRepository = UPStudentRecordsRepository(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(histogramUrl: String?) = flow {
        if (histogramUrl == null) { return@flow }
        val response = withContext(dispatcher) {
            repository.getDisciplineTestHistogram(histogramUrl)
        }
        emit(response.body())
    }.toUIStateFlow()
}