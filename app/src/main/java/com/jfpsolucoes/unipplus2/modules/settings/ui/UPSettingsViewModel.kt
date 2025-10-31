package com.jfpsolucoes.unipplus2.modules.settings.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.common.model.UPAppSession
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPEntityTransformers
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UPSettingsViewModel(
    private val database: EncryptedDataBase = EncryptedDataBase.shared,
    private val biometricManager: UPBiometricManager = UPBiometricManagerImpl
): ViewModel() {

    private val _userProfile = database.userProfileDao().get()
        .filterNotNull()
        .toUIStateFlow()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())

    private val _settings = database.settingsDao().get()
        .filterNotNull()
        .collectAsMutableStateFlow(viewModelScope, UPSettingsEntity())

    private val _biometricAvailable = biometricManager.isBiometricAvailable.mutableStateFlow

    private val _biometricEnabled = _settings
        .map { it.biometricEnabled }
        .collectAsMutableStateFlow(viewModelScope, false)

    val biometricAvailable = _biometricAvailable.asStateFlow()

    val biometricEnabled = _biometricEnabled.asStateFlow()

    val userProfile = _userProfile.asStateFlow()

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