package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.ads.UPAdManager
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.modules.home.domain.UPHomeGetSystemsUseCase
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UPHomeViewModelImpl(
    private val getSystemsUseCase: UPHomeGetSystemsUseCase = UPHomeGetSystemsUseCase(),
    private val database: EncryptedDataBase = EncryptedDataBase.shared
): UPHomeViewModel, ViewModel() {
    private val _settings = database.settingsDao().get()
        .filterNotNull()
        .collectAsMutableStateFlow(viewModelScope, UPSettingsEntity())

    private val _systemsState = getSystemsUseCase()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())

    private val _biometricDialogEnabled = _settings
        .map { it.biometricDialogEnabled }
        .collectAsMutableStateFlow(viewModelScope, false)

    private val _systemSelected = _systemsState
        .map { it.data?.feature?.firstOrNull() }
        .collectAsMutableStateFlow(viewModelScope, null)

    private val _shouldSignOut = MutableStateFlow(false)

    override val shouldSignOut = _shouldSignOut.asStateFlow()

    override val systemsState = _systemsState.asStateFlow()

    override val systemSelected = _systemSelected.asStateFlow()

    override val biometricDialogEnabled = _biometricDialogEnabled.asStateFlow()

    override val adsEnabled = UPAdManager.adsEnabled.asStateFlow()

    override fun getSystems() {
        getSystemsUseCase()
            .collectToFlow(_systemsState, viewModelScope)
    }

    override fun updateSettings(settings: UPSettingsEntity) = viewModelScope.launch {
        database.settingsDao().insert(settings)
    }

    override fun onSelectedSystem(system: UPSystem) {
        _systemSelected.update { system }
    }

    override suspend fun onSignOut() {
        database.settingsDao().insert(_settings.value.copy(
            autoSignIn = false
        ))
        _shouldSignOut.update { true }
    }
}