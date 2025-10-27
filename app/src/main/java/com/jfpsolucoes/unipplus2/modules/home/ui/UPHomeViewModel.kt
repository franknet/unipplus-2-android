package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPEntityTransformers
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.modules.home.domain.UPHomeGetSystemsUseCase
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemDeeplink
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UPHomeViewModel(
    private val getSystemsUseCase: UPHomeGetSystemsUseCase = UPHomeGetSystemsUseCase(),
    private val database: EncryptedDataBase = EncryptedDataBase.shared
): ViewModel() {
    private val _storedSettings = database.settingsDao().getAll().map(UPEntityTransformers::settings)

    private val _settings = UPSettingsEntity().mutableStateFlow

    private val _systems = MutableStateFlow<UIState<UPHomeSystemsResponse>>(UIState.UIStateNone())

    private val _systemSelected = MutableStateFlow<UPSystem?>(null)

    private val _biometricDialogEnabled = false.mutableStateFlow

    val systems = _systems.asStateFlow()

    val systemSelected = _systemSelected.asStateFlow()

    val biometricDialogEnabled = _biometricDialogEnabled.asStateFlow()

    init {
        getSettings()
        getSystems()
    }

    private fun getSettings() = viewModelScope.launch {
        _storedSettings.onEach { settings ->
            _settings.value = settings
            _biometricDialogEnabled.value = settings.biometricDialogEnabled
        }.collect()
    }

    private fun setSystemSelected(systemsState: UIState<UPHomeSystemsResponse>) {
        if (systemsState !is UIState.UIStateSuccess) { return }
        val data = systemsState.data ?: return
        val features = data.feature ?: return
        _systemSelected.value = features.firstOrNull()
    }

    fun getSystems() = viewModelScope.launch {
        getSystemsUseCase().onEach {
            _systems.value = it
            setSystemSelected(it)
        }.collect()
    }

    fun updateSettings() = viewModelScope.launch {
         val settingsUpdated = _settings.value.copy(biometricDialogEnabled = false)
        database.settingsDao().insert(settingsUpdated)
    }

    fun onClickOKBiometricDialog() = viewModelScope.launch {
        val data = _systems.value.data ?: return@launch
        val features = data.feature ?: return@launch
        val settings = features.firstOrNull {
            it.deeplink ==  UPSystemDeeplink.SETTINGS
        } ?: return@launch
        updateSettings()
        onSelectedSystem(settings)
    }

    fun onSelectedSystem(system: UPSystem) {
        _systemSelected.value = system
    }
}