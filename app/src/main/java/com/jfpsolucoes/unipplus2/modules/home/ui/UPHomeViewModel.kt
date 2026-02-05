package com.jfpsolucoes.unipplus2.modules.home.ui

import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

interface UPHomeViewModel {
    val systemsState: StateFlow<UIState<UPHomeSystemsResponse>>

    val systemSelected: StateFlow<UPSystem?>

    val biometricDialogEnabled: StateFlow<Boolean>

    fun onSignOut(): Job

    fun getSystems()

    fun updateSettings(settings: UPSettingsEntity): Job

    fun onClickOKBiometricDialog(): Job

    fun onSelectedSystem(system: UPSystem)
}