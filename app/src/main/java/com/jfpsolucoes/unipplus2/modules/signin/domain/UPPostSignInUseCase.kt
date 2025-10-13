package com.jfpsolucoes.unipplus2.modules.signin.domain

import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.data.UPSingInRepository
import com.jfpsolucoes.unipplus2.modules.signin.domain.repository.UPSignInRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPPostSignInUseCase(
    private val repository: UPSingInRepository = UPSignInRepositoryImpl(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(credentials: UPCredentialsEntity) = flow {
        val data = withContext(dispatcher) {
            repository.signIn(credentials)
        }
        emit(data)
    }.toUIStateFlow()
}