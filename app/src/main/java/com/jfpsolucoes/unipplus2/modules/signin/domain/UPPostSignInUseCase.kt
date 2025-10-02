package com.jfpsolucoes.unipplus2.modules.signin.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.data.UPSingInRepository
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPCredentials
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInResponse
import com.jfpsolucoes.unipplus2.modules.signin.domain.repository.UPSignInRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPPostSignInUseCase(
    private val repository: UPSingInRepository = UPSignInRepositoryImpl(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(credentials: UPCredentials) = flow<UPSignInResponse> {
        val data = withContext(dispatcher) {
            repository.signIn(credentials)
        }
        emit(data)
    }.toUIStateFlow()
}