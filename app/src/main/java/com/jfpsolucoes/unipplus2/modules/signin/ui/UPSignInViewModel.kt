package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
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
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class SignInViewModel(
    private val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase(),
    database: EncryptedDataBase = EncryptedDataBase.shared,
    private val biometricManager: UPBiometricManager = UPBiometricManagerImpl
) : ViewModel() {
    private val _credentials = database.credentialsDao().get()
        .filterNotNull()
        .collectAsMutableStateFlow(viewModelScope, UPCredentialsEntity())

    private val _settings = database.settingsDao().get()
        .filterNotNull()
        .collectAsMutableStateFlow(viewModelScope, UPSettingsEntity())

    private val _userProfileState = database.userProfileDao().get()
        .map { it ?: UPUserProfileEntity() }
        .toUIStateFlow()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())

    private val _signInState = MutableStateFlow<UIState<UPUserProfileEntity>>(UIState.UIStateNone())

    private val _biometricEnabled = _settings.map {
        it.biometricEnabled
    }.collectAsMutableStateFlow(viewModelScope, false)

    private val _biometricError = MutableStateFlow<String?>(null)

    private val _rgText = _credentials
        .map { it.rg }
        .collectAsMutableStateFlow(viewModelScope, "")

    private val _passwordText = "".mutableStateFlow

    var snackbarState = SnackbarHostState()

    var biometricEnabled = _biometricEnabled.asStateFlow()

    var biometricError = _biometricError.asStateFlow()

    var signInState = _signInState.asStateFlow()

    val userProfileState = _userProfileState.asStateFlow()

    val rgText = _rgText.asStateFlow()

    val passwordText = _passwordText.asStateFlow()

    fun onEditRg(value: String) {
        val credentialsUpdated = _credentials.value.copy(rg = value)
        _credentials.value = credentialsUpdated
    }

    fun onEditPassword(value: String) {
        val credentialsUpdated = _credentials.value.copy(password = value)
        _credentials.value = credentialsUpdated
        _passwordText.value = value
    }

    fun requestBiometricAuthentication(context: AppCompatActivity) {
        biometricManager.authenticate(
            context,
            onSuccess = { performSignIn() },
            onError = { _, error ->
                _biometricError.value = error
                _biometricEnabled.value = false
                onEditPassword("")
            },
            onFailed = { },
            onCancel = {
                _biometricEnabled.value = false
                onEditPassword("")
            }
        )
    }

    fun performSignIn(activity: AppCompatActivity? = null) {
        if (_biometricEnabled.value && activity != null) {
            requestBiometricAuthentication(activity)
            return
        }
        postSignInUseCase(_credentials.value)
            .collectToFlow(_signInState, viewModelScope)
    }

    fun resetLoginState()  {
        _signInState.value = UIState.UIStateNone()
    }

    fun resetBiometricState() {
        _biometricError.value = null
        _biometricEnabled.value = true
    }
}