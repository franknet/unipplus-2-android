package com.jfpsolucoes.unipplus2.modules.settings.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UPSettingsViewModel(
    private val database: EncryptedDataBase = EncryptedDataBase.shared,
    private val firebaseDataBase: UPFirebaseDatabase = UPFirebaseDatabase,
    private val biometricManager: UPBiometricManager = UPBiometricManagerImpl
): ViewModel() {

    private val _userProfile = firebaseDataBase.userProfile
        .filterNotNull()
        .toUIStateFlow()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())

    private val _settings = database.settingsDao().get()
        .filterNotNull()
        .collectAsMutableStateFlow(viewModelScope, UPSettingsEntity())

    private val _biometricAvailable = biometricManager.isBiometricAvailable.mutableStateFlow

    val snackbarState = SnackbarHostState()

    val settings = _settings.asStateFlow()

    val biometricAvailable = _biometricAvailable.asStateFlow()

    val userProfile = _userProfile.asStateFlow()

    fun requestBiometricAuthentication(context: AppCompatActivity) {
        biometricManager.authenticate(
            context,
            subtitle = context.getString(R.string.biometric_toggle_subtitle_text),
            onSuccess = { updateBiometricSettings(true) },
            onError = { _, message ->
                showSnackbar(message)
                updateBiometricSettings(false)
           },
            onFailed = {
                updateBiometricSettings(false)
            },
            onCancel = {
                updateBiometricSettings(false)
            }
        )
    }

    fun updateBiometricSettings(checked: Boolean) = viewModelScope.launch {
        _settings.update { it.copy(
            autoSignIn = false,
            biometricEnabled = checked
        ) }
        database.settingsDao().insert(_settings.value)
    }

    fun onAutoSignCheckedChange(checked: Boolean) = viewModelScope.launch {
        _settings.update { it.copy(
            autoSignIn = checked,
            biometricEnabled = false
        ) }
        database.settingsDao().insert(_settings.value)
    }

    fun showSnackbar(message: String) = viewModelScope.launch {
        val errorVisual = UPSnackbarVisual(message = message)
        snackbarState.showSnackbar(errorVisual)
    }

}