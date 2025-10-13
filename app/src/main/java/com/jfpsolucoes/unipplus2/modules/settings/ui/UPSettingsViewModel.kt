package com.jfpsolucoes.unipplus2.modules.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.common.model.UPAppSession
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UPSettingsViewModel(
    private val database: EncryptedDataBase = EncryptedDataBase.shared,
): ViewModel() {
    private val mSettings = database.settingsDao().getAll()
        .map { it.lastOrNull() }

    var settings: UPSettingsEntity? = null

    val biometricEnabled = false.mutableState
    var biometricLaunchCount = 0.mutableState
    val userName = UPAppSession.data?.user?.name.value

    init {
        viewModelScope.launch {
            mSettings.collect {
                settings = it
                biometricEnabled.value = it?.biometricEnabled ?: false
            }
        }
    }

    fun onBiometricToggle(enabled: Boolean) {
        if (enabled) {
            biometricLaunchCount.value += 1
        } else {
            updateBiometricSettings(false)
        }
    }

    fun updateBiometricSettings(enabled: Boolean) = viewModelScope.launch {
        val updateSettings = (settings ?: UPSettingsEntity()).copy(biometricEnabled = enabled)
        database.settingsDao().insert(updateSettings)
    }

}