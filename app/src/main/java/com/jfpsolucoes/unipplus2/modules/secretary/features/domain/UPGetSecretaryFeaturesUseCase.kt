package com.jfpsolucoes.unipplus2.modules.secretary.features.domain

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.secretary.features.data.UPSecretaryRepository
import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.repository.UPSecretaryRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPGetSecretaryFeaturesUseCase(
    val repository: UPSecretaryRepository = UPSecretaryRepositoryImpl(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        val data = withContext(dispatcher) {
            repository.getSecretaryFeatures()
        }
        emit(data)
    }.toUIStateFlow()
}