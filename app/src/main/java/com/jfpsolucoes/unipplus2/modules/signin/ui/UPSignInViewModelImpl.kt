package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.debugPrint
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class SignInViewModelImpl(
    private val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase(),
    private val database: EncryptedDataBase = EncryptedDataBase.shared,
    private val biometricManager: UPBiometricManager = UPBiometricManagerImpl
) : UPSignInViewModel, ViewModel() {
    private val _credentials = database.credentialsDao().get()
        .map { it ?: UPCredentialsEntity() }
        .collectAsMutableStateFlow(viewModelScope, UPCredentialsEntity())

    private val _settings = database.settingsDao().get()
        .map { it ?: UPSettingsEntity() }
        .collectAsMutableStateFlow(viewModelScope, UPSettingsEntity())

    private val _userProfileState = database.userProfileDao().get()
        .map { it ?: UPUserProfileEntity() }
        .toUIStateFlow()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())

    private val _signInState = MutableStateFlow<UIState<UPUserProfileEntity>>(UIState.UIStateNone())

    private val _biometricError = MutableStateFlow<String?>(null)

    private val _rgText = "".mutableStateFlow

    private val _passwordText = "".mutableStateFlow

    private val _showPasswordField = _settings
        .map { !(it.biometricEnabled || it.autoSignIn) }
        .debugPrint("SignInViewModelImpl")
        .collectAsMutableStateFlow(
            scope = viewModelScope,
            initialValue = true
        )

    override val settings = _settings.asStateFlow()

    override val snackbarState = SnackbarHostState()

    override val signInState = _signInState.asStateFlow()

    override val userProfileState = _userProfileState.asStateFlow()

    override val rgText = _rgText.asStateFlow()

    override val passwordText = _passwordText.asStateFlow()

    override val showPasswordField = _showPasswordField.asStateFlow()

    override fun onEditRg(value: String) {
        _rgText.update { value }
        _credentials.update { it.copy(rg = value) }
    }

    override fun onEditPassword(value: String) {
        _passwordText.update { value }
        _credentials.update { it.copy(password = value) }
    }

    override fun requestBiometricAuthentication(context: AppCompatActivity) {
        biometricManager.authenticate(
            context,
            onSuccess = { performSignIn(null) },
            onError = { _, error ->
                onEditPassword("")
                showSnackbar(error)
            },
            onFailed = { },
            onCancel = {
                onEditPassword("")
                _showPasswordField.value = true
            }
        )
    }

    override fun performSignIn(activity: AppCompatActivity?) {
        if (_settings.value.biometricEnabled && !_settings.value.autoSignIn && activity != null) {
            requestBiometricAuthentication(activity)
            return
        }
        if (!_settings.value.biometricEnabled && !_settings.value.autoSignIn && _passwordText.value.isEmpty()) {
            return
        }
        postSignInUseCase(_credentials.value)
            .collectToFlow(_signInState, viewModelScope)
    }

    override fun resetLoginState()  {
        _passwordText.update { "" }
        _signInState.update { UIState.UIStateNone() }
    }

    override fun resetBiometricState() {
        _biometricError.update { null }
    }

    override fun showSnackbar(message: String) {
        viewModelScope.launch {
            val result = snackbarState.showSnackbar(
                UPSnackbarVisual(message = message)
            )
            if (result == SnackbarResult.Dismissed) {
                resetLoginState()
            }
        }
    }

    override fun onChangeAutoSignIn(checked: Boolean) {
        viewModelScope.launch {
            if (_credentials.value.password.isEmpty()) {
                return@launch
            }
            database.settingsDao().insert(_settings.value.copy(
                autoSignIn = checked
            ))
        }
    }
}