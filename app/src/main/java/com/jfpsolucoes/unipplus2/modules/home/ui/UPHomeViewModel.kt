package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPEntityTransformers
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.modules.home.domain.UPHomeGetSystemsUseCase
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemDeeplink
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch

class UPHomeViewModel(
    private val getSystemsUseCase: UPHomeGetSystemsUseCase = UPHomeGetSystemsUseCase(),
    private val database: EncryptedDataBase = EncryptedDataBase.shared
): ViewModel() {
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

    val systems = _systemsState.asStateFlow()

    val systemSelected = _systemSelected.asStateFlow()

    val biometricDialogEnabled = _biometricDialogEnabled.asStateFlow()

    fun getSystems() {
        getSystemsUseCase().collectToFlow(_systemsState, viewModelScope)
    }

    fun updateSettings() = viewModelScope.launch {
         val settingsUpdated = _settings.value.copy(biometricDialogEnabled = false)
        database.settingsDao().insert(settingsUpdated)
    }

    fun onClickOKBiometricDialog() = viewModelScope.launch {
        val data = _systemsState.value.data ?: return@launch
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