package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPCredentials
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInResponse
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase()
): ViewModel() {
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

    var signInState = MutableStateFlow<UIState<UPSignInResponse>>(UIState.UIStateNone())

    fun performSignIn() {
        postSignInUseCase(UPCredentials(idText.value, passwordText.value))
            .collectToFlow(signInState, viewModelScope)
    }

    fun resetLoginState() = viewModelScope.launch {
        signInState.emit(UIState.UIStateNone())
    }

}