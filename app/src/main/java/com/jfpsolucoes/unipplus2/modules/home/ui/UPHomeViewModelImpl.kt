package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.compose.ui.text.style.TextDecoration.Companion.combine
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    private val _shouldSignOut = MutableSharedFlow<Boolean>()

    override val shouldSignOut = combine(
        _shouldSignOut,
        _settings
    ) { shouldSignOut, settings ->
        shouldSignOut && !settings.autoSignIn
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = false
    )

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
        database.settingsDao().insert(_settings.value.copy(
            signedIn = false,
            autoSignIn = false
        ))
        _shouldSignOut.emit(true)
    }
}