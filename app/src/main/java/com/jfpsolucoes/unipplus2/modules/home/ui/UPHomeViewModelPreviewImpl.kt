package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.utils.extensions.stateFlow
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UPHomeViewModelPreviewImpl(
    override val systemsState: StateFlow<UIState<UPHomeSystemsResponse>> = UIState.UIStateNone<UPHomeSystemsResponse>().stateFlow,
    override val systemSelected: StateFlow<UPSystem?> = null.stateFlow,
    override val biometricDialogEnabled: StateFlow<Boolean> = false.stateFlow
) : UPHomeViewModel, ViewModel() {
    override fun getSystems() {
        TODO("Not yet implemented")
    }

    override fun updateSettings() = viewModelScope.launch {
        TODO("Not yet implemented")
    }

    override fun onClickOKBiometricDialog() = viewModelScope.launch {
        TODO("Not yet implemented")
    }

    override fun onSelectedSystem(system: UPSystem) {
        TODO("Not yet implemented")
    }

}
