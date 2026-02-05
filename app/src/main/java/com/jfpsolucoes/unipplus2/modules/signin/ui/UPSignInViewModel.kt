package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface UPSignInViewModel {
    val settings: StateFlow<UPSettingsEntity>

    val snackbarState: SnackbarHostState

    val signInState: StateFlow<UIState<UPUserProfileEntity>>

    val userProfileState: StateFlow<UIState<UPUserProfileEntity>>

    val rgText: StateFlow<String>

    val passwordText: StateFlow<String>

    val showPasswordField: StateFlow<Boolean>

    fun onEditRg(value: String)

    fun onEditPassword(value: String)

    fun requestBiometricAuthentication(context: AppCompatActivity)

    fun performSignIn(activity: AppCompatActivity?)

    fun resetLoginState()

    fun resetBiometricState()

    fun showSnackbar(message: String)

    fun onChangeAutoSignIn(checked: Boolean)
}