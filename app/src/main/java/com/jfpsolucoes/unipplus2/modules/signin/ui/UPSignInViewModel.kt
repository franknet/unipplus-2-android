package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPCredentials
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.SignInResponse
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

private val ON_SITE_CREDENTIALS: UPCredentials = UPCredentials("F341720", "Joao28022002.")
private val REMOTE_CREDENTIALS: UPCredentials = UPCredentials("242983", "M3lzinh@2001")

class SignInViewModel(
    private val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase(),
    var navController: NavController? = null
): ViewModel() {
    var snackbarState = SnackbarHostState()
    var idText = "F341720".mutableStateFlow
    var passwordText = "Joao28022002.".mutableStateFlow

    fun onEditId(id: String) {
        idText.value = id
    }

    fun onEditPassword(password: String) {
        passwordText.value = password
    }

    var signInState = MutableStateFlow<UIState<SignInResponse>>(UIState.UIStateNone())

    fun performSignIn() {
        postSignInUseCase(UPCredentials(idText.value, passwordText.value))
            .flowOn(Dispatchers.IO)
            .collectToFlow(signInState, viewModelScope)

    }

    fun resetLoginState() = viewModelScope.launch {
        signInState.emit(UIState.UIStateNone())
    }

}