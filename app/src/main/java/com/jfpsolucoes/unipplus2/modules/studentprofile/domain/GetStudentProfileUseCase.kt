package com.jfpsolucoes.unipplus2.modules.studentprofile.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.studentprofile.data.UPStudentProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetStudentProfileUseCase(
    private val repository: UPStudentProfileRepository = UPStudentProfileRepository(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        val response = withContext(dispatcher) {
            repository.getStudentProfile()
        }
        val data = response.body()
        emit(data)
    }.toUIStateFlow()
}

