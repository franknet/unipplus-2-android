package com.jfpsolucoes.unipplus2.modules.signin.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.data.UPSingInRepository
import com.jfpsolucoes.unipplus2.modules.signin.domain.repository.UPSignInRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPSignInGetSessionUseCase(
    private val repository: UPSingInRepository = UPSignInRepositoryImpl(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        val data = withContext(dispatcher) {
            repository.getSession()
        }
        emit(data)
    }.toUIStateFlow()
}