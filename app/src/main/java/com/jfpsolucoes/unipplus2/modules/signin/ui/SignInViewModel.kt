package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.domain.PostSignInUseCase
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPSignInGetSessionUseCase
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.Credentials
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private val IN_SITE_CREDENTIALS: Credentials = Credentials("F341720", "Joao28022002.")
private val EAD_CREDENTIALS: Credentials = Credentials("2429830", "M3lzinh@2001")

class SignInViewModel(
    private val postSignInUseCase: PostSignInUseCase = PostSignInUseCase(),
    private val getSessionUseCase: UPSignInGetSessionUseCase = UPSignInGetSessionUseCase(),
    var navController: NavController? = null
): ViewModel() {
    var idText = "".mutableStateFlow
    var passwordText = "".mutableStateFlow

    fun onEditId(id: String) {
        idText.value = id
    }

    fun onEditPassword(password: String) {
        passwordText.value = password
    }

    var signInState = MutableStateFlow<UIState<String>>(UIState.UIStateNone())

    fun performSignIn() {
        navController?.navigate(R.id.action_UPSignInFragment_to_UPHomeFragment)
    }

    fun resetLoginState() = viewModelScope.launch {
        signInState.emit(UIState.UIStateNone())
    }

}