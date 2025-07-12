package com.jfpsolucoes.unipplus2.modules.home.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.home.data.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetSystemsUseCase(
    private val repository: HomeRepository = HomeRepository(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        val response = withContext(dispatcher) {
            repository.getSystems()
        }
        emit(response.body())
    }.toUIStateFlow()
}