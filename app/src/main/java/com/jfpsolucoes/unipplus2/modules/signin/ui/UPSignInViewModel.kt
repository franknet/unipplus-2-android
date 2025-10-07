package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.common.model.UPAppInfo
import com.jfpsolucoes.unipplus2.core.database.SHARED_KEY_APP_INFO
import com.jfpsolucoes.unipplus2.core.database.SHARED_KEY_APP_SHARE_ALERT_COUNT
import com.jfpsolucoes.unipplus2.core.database.SharedPreferencesManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPCredentials
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInResponse
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SignInViewModel(
    private val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase()
) : ViewModel() {
    var snackbarState = SnackbarHostState()
//    var idText = "D49HCD0".mutableStateFlow
//    var passwordText = "Geo237138@".mutableStateFlow

    var idText = "2429868".mutableStateFlow
    var passwordText = "Branco@bebold2024".mutableStateFlow

    fun onEditId(id: String) {
        idText.value = id
    }

    fun onEditPassword(password: String) {
        passwordText.value = password
    }

    private val _signInState = MutableStateFlow<UIState<UPSignInResponse>>(UIState.UIStateNone())
    var signInState = _signInState.asStateFlow()

    init {
        _signInState.onEach {
            if (it is UIState.UIStateSuccess) {
                // Create app session
                val session = UPAppInfo(it.data)
                SharedPreferencesManager.saveObject(SHARED_KEY_APP_INFO, session)

                // Add share alert count after login
                val shareAlertCount = SharedPreferencesManager.getInt(SHARED_KEY_APP_SHARE_ALERT_COUNT)
                SharedPreferencesManager.setInt(SHARED_KEY_APP_SHARE_ALERT_COUNT, shareAlertCount + 1)
            }
        }
    }

    fun performSignIn() {
        postSignInUseCase(UPCredentials(idText.value, passwordText.value))
            .collectToFlow(_signInState, viewModelScope)
    }

    fun resetLoginState() = viewModelScope.launch {
        _signInState.emit(UIState.UIStateNone())
    }

}