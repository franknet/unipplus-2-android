package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableState
import com.jfpsolucoes.unipplus2.modules.home.domain.UPHomeGetSystemsUseCase
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UPHomeViewModel(
    private val getSystemsUseCase: UPHomeGetSystemsUseCase = UPHomeGetSystemsUseCase(),
    private val database: EncryptedDataBase = EncryptedDataBase.shared
): ViewModel() {
    private val mSettings = database.settingsDao().getAll()
        .map { it.lastOrNull() }

    private var mStoredSettings: UPSettingsEntity? = null

    init {
        viewModelScope.launch {
            mSettings.collect { settings ->
                mStoredSettings = settings
                biometricDialogShowed.value = settings?.biometricDialogShowed ?: false
            }
        }
    }

    var biometricDialogShowed = true.mutableState

    private val _systemsState = MutableStateFlow<UIState<UPHomeSystemsResponse>>(UIState.UIStateNone())
    val systemsState = _systemsState.asStateFlow()

    private val _selectedSystem = MutableStateFlow<UPSystem?>(null)

    val selectedSystem = _selectedSystem.asStateFlow()

    fun updateSettings() = viewModelScope.launch {
        val updateSettings = mStoredSettings?.copy(biometricDialogShowed = true)
        updateSettings?.let {
            database.settingsDao().update(it)
        }
    }

    fun fetchSystems() {
        getSystemsUseCase().collectToFlow(_systemsState, viewModelScope)
    }

}