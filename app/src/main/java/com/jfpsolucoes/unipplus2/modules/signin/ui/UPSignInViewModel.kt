package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.common.model.UPAppSession
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPEntityTransformers
import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.extensions.bindTo
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.debugPrint
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInResponse
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class SignInViewModel(
    private val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase(),
    private val database: EncryptedDataBase = EncryptedDataBase.shared,
    private val biometricManager: UPBiometricManager = UPBiometricManagerImpl
) : ViewModel() {
    private val _storedCredentials = database.credentialsDao().getAll().map(UPEntityTransformers::credentials)

    private val _storedSettings = database.settingsDao().getAll().map(UPEntityTransformers::settings)

    private var _credentials = UPCredentialsEntity().mutableStateFlow

    private val _signInState = MutableStateFlow<UIState<UPSignInResponse>>(UIState.UIStateNone())

    private val _rgText = "".mutableStateFlow

    private val _passwordText = "".mutableStateFlow

    private val _biometricEnabled = false.mutableStateFlow

    var snackbarState = SnackbarHostState()

    var signInState = _signInState.asStateFlow()

    val biometricEnabled = _biometricEnabled.asStateFlow()

    val rgText = _rgText.asStateFlow()

    val passwordText = _passwordText.asStateFlow()

    init {
        viewModelScope.launch {
            _storedCredentials.onEach {
                _credentials.value = it
                _rgText.value = it.rg
            }.collect()

            _storedSettings.onEach {
                _biometricEnabled.value = it.biometricEnabled
            }.collect()
        }
    }

    private fun updateCredentials(): UPCredentialsEntity {
        return if (_biometricEnabled.value) _credentials.value else _credentials.value.copy(
            rg = _rgText.value,
            password = _passwordText.value
        )
    }

    fun onEditRg(value: String) {
        _rgText.value = value
    }

    fun onEditPassword(value: String) {
        _passwordText.value = value
    }

    fun requestBiometricAuthentication(context: AppCompatActivity) {
        biometricManager.authenticate(
            context,
            onSuccess = ::performSignIn,
            onError = { _, _ -> },
            onFailed = { },
            onCancel = {  }
        )
    }

    fun performSignIn() = viewModelScope.launch {
        val updatedCredentials = updateCredentials()
        postSignInUseCase(updatedCredentials).collect { signInState ->
            if (signInState is UIState.UIStateSuccess) {
                UPAppSession.data = signInState.data
                database.credentialsDao().insert(updatedCredentials)
            }
            _signInState.emit(signInState)
        }
    }

    fun resetLoginState() = viewModelScope.launch {
        _signInState.emit(UIState.UIStateNone())
    }

}