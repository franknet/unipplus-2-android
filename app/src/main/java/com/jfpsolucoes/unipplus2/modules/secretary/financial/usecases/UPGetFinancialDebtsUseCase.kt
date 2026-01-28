package com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.secretary.features.data.UPSecretaryRepository
import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.repository.UPSecretaryRepositoryImpl
import com.jfpsolucoes.unipplus2.modules.secretary.financial.data.UPFinancialRepository
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.repository.UPFinancialRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPGetFinancialDebtsUseCase(
    val repository: UPFinancialRepository = UPFinancialRepositoryImpl(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        val data = withContext(dispatcher) {
            repository.getDebts()
        }
        emit(data)
    }.toUIStateFlow()
}