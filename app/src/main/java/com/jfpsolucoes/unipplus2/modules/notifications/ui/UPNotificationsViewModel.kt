package com.jfpsolucoes.unipplus2.modules.notifications.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.modules.notifications.domain.UPGetNotificationsUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class UPNotificationsViewModel(
    private val getNotificationsUseCase: UPGetNotificationsUseCase = UPGetNotificationsUseCase(),
): ViewModel() {
    val notificationsUIState = getNotificationsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UIState.UIStateNone())
}