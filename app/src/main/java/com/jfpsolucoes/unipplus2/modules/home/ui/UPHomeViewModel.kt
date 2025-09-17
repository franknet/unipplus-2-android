package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.modules.home.domain.UPHomeGetSystemsUseCase
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UPHomeViewModel(
    private val getSystemsUseCase: UPHomeGetSystemsUseCase = UPHomeGetSystemsUseCase()
): ViewModel() {

    private val _systemsState = MutableStateFlow<UIState<UPHomeSystemsResponse>>(UIState.UIStateNone())
    val systemsState = _systemsState.asStateFlow()

    private val _selectedSystem = MutableStateFlow<UPSystem?>(null)
    val selectedSystem = _selectedSystem.asStateFlow()

    fun fetchSystems() {
        getSystemsUseCase().collectToFlow(_systemsState, viewModelScope)
    }

}