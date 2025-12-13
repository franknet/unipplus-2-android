package com.jfpsolucoes.unipplus2.modules.home.ui

import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

interface UPHomeViewModel {
    val systemsState: StateFlow<UIState<UPHomeSystemsResponse>>

    val systemSelected: StateFlow<UPSystem?>

    val biometricDialogEnabled: StateFlow<Boolean>

    fun getSystems()

    fun updateSettings(): Job

    fun onClickOKBiometricDialog(): Job

    fun onSelectedSystem(system: UPSystem)
}