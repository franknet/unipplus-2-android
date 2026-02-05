package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.stateFlow
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.StateFlow

class UPSignInViewModelPreviewImpl(
    override val signInState: StateFlow<UIState<UPUserProfileEntity>> = UIState.UIStateNone<UPUserProfileEntity>().stateFlow,
    override val userProfileState: StateFlow<UIState<UPUserProfileEntity>> = UIState.UIStateNone<UPUserProfileEntity>().stateFlow
): UPSignInViewModel, ViewModel() {

    override val settings = UPSettingsEntity().stateFlow

    override var snackbarState = SnackbarHostState()

    override val rgText = "Teste".stateFlow

    override val passwordText = "".stateFlow

    override val showPasswordField = true.stateFlow

    override fun onEditRg(value: String) {
        TODO("Not yet implemented")
    }

    override fun onEditPassword(value: String) {
        TODO("Not yet implemented")
    }

    override fun requestBiometricAuthentication(context: AppCompatActivity) {
        TODO("Not yet implemented")
    }

    override fun performSignIn(activity: AppCompatActivity?) {
        TODO("Not yet implemented")
    }

    override fun resetLoginState() {
        TODO("Not yet implemented")
    }

    override fun resetBiometricState() {
        TODO("Not yet implemented")
    }

    override fun showSnackbar(message: String) {
        TODO("Not yet implemented")
    }

    override fun onChangeAutoSignIn(checked: Boolean) {
        TODO("Not yet implemented")
    }
}