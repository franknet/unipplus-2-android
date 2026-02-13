package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UPSignInViewModel(
    val localDatabase: EncryptedDataBase = EncryptedDataBase.shared,
    val remoteDataBase: UPFirebaseDatabase = UPFirebaseDatabase,
    val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase(),
    val biometricManager: UPBiometricManager? = UPBiometricManagerImpl
): ViewModel() {
    private val _credentials = localDatabase.credentialsDao().get()
        .filterNotNull()
        .map(::credentialsChangedListener)
        .collectAsMutableStateFlow(
            scope = viewModelScope,
            initialValue = UPCredentialsEntity()
        )

    private val _settings = localDatabase.settingsDao().get()
        .filterNotNull()
        .collectAsMutableStateFlow(
            scope = viewModelScope,
            initialValue = UPSettingsEntity()
        )

    private val _rgText = MutableStateFlow("")

    private val _passwordText = MutableStateFlow("")

    private val _showPasswordField = _settings
        .map { !it.autoSignIn.or(it.biometricEnabled) }
        .collectAsMutableStateFlow(
            scope = viewModelScope,
            initialValue = true
        )

    private val _userProfile = remoteDataBase.userProfile

    private val _sinInUIState = MutableStateFlow<UIState<UPUserProfileEntity>>(UIState.UIStateNone())

    val rgText = _rgText.asStateFlow()

    val passwordText = _passwordText.asStateFlow()

    val showPasswordField = _showPasswordField.asStateFlow()

    val settings = _settings
        .asStateFlow()

    val userProfile = _userProfile
        .asStateFlow()

    val singInUIState = _sinInUIState.asStateFlow()

    fun updateRg(text: String) {
        _credentials.update { it.copy(rg = text) }
        _rgText.update { text }
    }

    fun updatePassword(text: String) {
        _credentials.update { it.copy(password = text) }
        _passwordText.update { text }
    }

    fun updateSettings(settings: UPSettingsEntity) = viewModelScope.launch {
        // Prevent auto signIn with empty password
        if (settings.autoSignIn && _passwordText.value.isEmpty()) {
            return@launch
        }
        // Disable biometric if auto sign in is enabled
        if (settings.autoSignIn.and(settings.biometricEnabled)) {
            _settings.update { settings.copy(biometricEnabled = false) }
            return@launch
        }
        _settings.update { settings }
    }

    fun resetSingInState() = viewModelScope.launch {
        _sinInUIState.update { UIState.UIStateNone() }
    }

    fun performBiometricAuthentication(context: AppCompatActivity) {
        biometricManager?.authenticate(
            context,
            onSuccess = {
                performSignIn()
            },
            onFailed = {},
            onError = { code, message ->
                resetAndShowPasswordField()
            },
            onCancel = ::resetAndShowPasswordField
        )
    }

    fun performSignIn() = viewModelScope.launch {
        postSignInUseCase(_credentials.value)
            .map(::signInStateChangedListener)
            .collectToFlow(_sinInUIState)
    }

    private fun credentialsChangedListener(credentials: UPCredentialsEntity): UPCredentialsEntity {
        // Start listening to remote user profile if there is stored credentials
        if (_userProfile.value == null && credentials.rg.isNotEmpty()) {
            remoteDataBase.startListeningUser(userRg = credentials.rg)
        }
        return credentials
    }

    private fun resetAndShowPasswordField() {
        _passwordText.update { "" }
        _credentials.update { it.copy(password = "") }
        _showPasswordField.update { true }
    }

    private suspend fun signInStateChangedListener(state: UIState<UPUserProfileEntity>): UIState<UPUserProfileEntity> {
        if (state.success) {
            _showPasswordField.update { !_settings.value.autoSignIn.or(_settings.value.biometricEnabled) }
            localDatabase.credentialsDao().insert(_credentials.value)
            localDatabase.settingsDao().insert(_settings.value)
        }
        if (state.failure) {
            if (_settings.value.autoSignIn) {
                _settings.update { it.copy(autoSignIn = false) }
            }
            resetAndShowPasswordField()
        }
        return state
    }
}