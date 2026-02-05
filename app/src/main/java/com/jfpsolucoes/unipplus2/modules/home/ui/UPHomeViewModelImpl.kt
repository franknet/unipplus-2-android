package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.modules.home.domain.UPHomeGetSystemsUseCase
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemDeeplink
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UPHomeViewModelImpl(
    private val getSystemsUseCase: UPHomeGetSystemsUseCase = UPHomeGetSystemsUseCase(),
    private val database: EncryptedDataBase = EncryptedDataBase.shared
): UPHomeViewModel, ViewModel() {
    private val _settings = database.settingsDao().get()
        .map { it ?: UPSettingsEntity() }
        .collectAsMutableStateFlow(viewModelScope, UPSettingsEntity())

    private val _systemsState = getSystemsUseCase()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())

    private val _biometricDialogEnabled = _settings
        .map { it.biometricDialogEnabled }
        .collectAsMutableStateFlow(viewModelScope, false)

    private val _systemSelected = _systemsState
        .map { it.data?.feature?.firstOrNull() }
        .collectAsMutableStateFlow(viewModelScope, null)

    override val systemsState = _systemsState.asStateFlow()

    override val systemSelected = _systemSelected.asStateFlow()

    override val biometricDialogEnabled = _biometricDialogEnabled.asStateFlow()

    override fun getSystems() {
        getSystemsUseCase().collectToFlow(_systemsState, viewModelScope)
    }

    override fun updateSettings(settings: UPSettingsEntity) = viewModelScope.launch {
        database.settingsDao().insert(settings)
    }

    override fun onClickOKBiometricDialog() = viewModelScope.launch {
        val data = _systemsState.value.data ?: return@launch
        val features = data.feature ?: return@launch
        val settings = features.firstOrNull {
            it.deeplink ==  UPSystemDeeplink.SETTINGS
        } ?: return@launch
        updateSettings(_settings.value.copy(biometricDialogEnabled = false))
        onSelectedSystem(settings)
    }

    override fun onSelectedSystem(system: UPSystem) {
        _systemSelected.value = system
    }

    override fun onSignOut() = viewModelScope.launch {
        updateSettings(_settings.value.copy(autoSignIn = false))
    }
}