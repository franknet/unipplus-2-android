package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.data.UPStudentRecordsRepository
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.UPStudentRecordsRepositoryImpl
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models.UPStudentRecordsDisciplinesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPStudentRecordsGetDisciplinesUseCase(
    val repository: UPStudentRecordsRepository = UPStudentRecordsRepositoryImpl(),
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow<UPStudentRecordsDisciplinesResponse> {
        val data = withContext(dispatcher) {
            repository.getDisciplines()
        }
        emit(data)
    }.toUIStateFlow()
}