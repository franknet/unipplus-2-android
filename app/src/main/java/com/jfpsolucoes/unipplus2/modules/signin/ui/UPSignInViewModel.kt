package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.common.model.UPAppSession
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInResponse
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class SignInViewModel(
    private val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase(),
    private val database: EncryptedDataBase = EncryptedDataBase.shared,
) : ViewModel() {
    private var mCredentials: UPCredentialsEntity? = null

    var storedCredentials = database.credentialsDao().getAll()
        .map { it.lastOrNull() }

    private val mSettings = database.settingsDao().getAll()
        .map { it.lastOrNull() }

    var snackbarState = SnackbarHostState()
    var rgText = "".mutableState
    var passwordText = "".mutableState

    val biometricEnabled = false.mutableState

    private val mSignInState = MutableStateFlow<UIState<UPSignInResponse>>(UIState.UIStateNone())
    var signInState = mSignInState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(storedCredentials, mSettings) { credentials, settings ->
                mCredentials = credentials
                rgText.value = credentials?.rg.value
                biometricEnabled.value = settings?.biometricEnabled ?: false
                if (settings == null) {
                    database.settingsDao().insert(UPSettingsEntity())
                }
            }.collect()
        }
    }


    fun onEditRg(value: String) {
        rgText.value = value
    }

    fun onEditPassword(value: String) {
        passwordText.value = value
    }

    fun performSignIn() = viewModelScope.launch {
        if (biometricEnabled.value) {
            passwordText.value = mCredentials?.password.value
        }
        val updateCredentials = (mCredentials ?: UPCredentialsEntity(rg = "", password = "")).copy(
            rg = rgText.value,
            password = passwordText.value
        )
        postSignInUseCase(updateCredentials)
            .collect { state ->
                if (state is UIState.UIStateSuccess) {
                    UPAppSession.data = state.data
                    database.credentialsDao().insert(updateCredentials)
                    mSignInState.emit(state)
                } else {
                    mSignInState.emit(state)
                }
            }
    }

    fun resetLoginState() = viewModelScope.launch {
        mSignInState.emit(UIState.UIStateNone())
    }

}