package com.jfpsolucoes.unipplus2.modules.dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.modules.notifications.domain.UPGetNotificationsUseCase
import com.jfpsolucoes.unipplus2.modules.studentprofile.domain.GetStudentProfileUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class UPDashboardViewModel(
    getStudentProfileUseCase: GetStudentProfileUseCase = GetStudentProfileUseCase(),
    getNotificationsUseCase: UPGetNotificationsUseCase = UPGetNotificationsUseCase()
): ViewModel() {
    val studentProfileUIState = getStudentProfileUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UIState.UIStateNone())

    val notificationsUIState = getNotificationsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UIState.UIStateNone())

    override fun onCleared() {
        super.onCleared()
    }

}