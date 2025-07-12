package com.jfpsolucoes.unipplus2.modules.sec.finance.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.sec.finance.data.UPFinanceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPFinanceGetBillsUseCase(
    private val repository: UPFinanceRepository = UPFinanceRepository(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        val response = withContext(dispatcher) {
            repository.getBills()
        }
        val data = response.body()
        emit(data)
    }.toUIStateFlow()
}