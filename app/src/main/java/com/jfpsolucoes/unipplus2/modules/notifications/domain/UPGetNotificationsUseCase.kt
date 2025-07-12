package com.jfpsolucoes.unipplus2.modules.notifications.domain;

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.notifications.data.UPNotificationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UPGetNotificationsUseCase(
    private val repository: UPNotificationsRepository = UPNotificationsRepository(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        val response = withContext(dispatcher) { repository.getNotifications() }
        val data = response.body()
        emit(data)
    }.toUIStateFlow()
}
