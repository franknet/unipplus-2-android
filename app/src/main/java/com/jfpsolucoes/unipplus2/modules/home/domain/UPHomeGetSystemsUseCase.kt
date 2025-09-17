package com.jfpsolucoes.unipplus2.modules.home.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.home.data.UPHomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPHomeGetSystemsUseCase(
    private val repository: UPHomeRepository = UPHomeRepositoryImpl(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        val data = withContext(dispatcher) {
            repository.getSystems()
        }
        emit(data)
    }.toUIStateFlow()
}