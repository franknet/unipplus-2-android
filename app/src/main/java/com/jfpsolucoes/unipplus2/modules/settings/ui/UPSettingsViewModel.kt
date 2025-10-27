package com.jfpsolucoes.unipplus2.modules.settings.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.common.model.UPAppSession
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPEntityTransformers
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UPSettingsViewModel(
    private val database: EncryptedDataBase = EncryptedDataBase.shared,
    private val biometricManager: UPBiometricManager = UPBiometricManagerImpl
): ViewModel() {
    private val _settingsSaved = database.settingsDao().getAll().map(UPEntityTransformers::settings)

    private val _settings = UPSettingsEntity().mutableStateFlow

    private val _biometricAvailable = biometricManager.isBiometricAvailable.mutableStateFlow

    private val _biometricEnabled = false.mutableStateFlow

    val biometricAvailable = _biometricAvailable.asStateFlow()

    val biometricEnabled = _biometricEnabled.asStateFlow()

    val userName = UPAppSession.data?.user?.name.value

    init {
        viewModelScope.launch {
            _settingsSaved.collect {
                _settings.value = it
                _biometricEnabled.value = it.biometricEnabled
            }
        }
    }

    fun requestBiometricAuthentication(context: AppCompatActivity) {
        biometricManager.authenticate(
            context,
            subtitle = context.getString(R.string.biometric_toggle_subtitle_text),
            onSuccess = { updateBiometricSettings(true) },
            onError = { _, message -> },
            onFailed = { },
            onCancel = { }
        )
    }

    fun updateBiometricSettings(enabled: Boolean, context: AppCompatActivity? = null) = viewModelScope.launch {
        if (enabled && context != null) {
            requestBiometricAuthentication(context)
        } else {
            val updateSettings = _settings.value.copy(biometricEnabled = enabled)
            database.settingsDao().insert(updateSettings)
        }
    }

}