package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.jfpsolucoes.unipplus2.core.utils.extensions.ShowSnackbar
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInCredentialsProperties
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInCredentials
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInLogo
import com.jfpsolucoes.unipplus2.ui.LocalSnackbarState
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.fragment.UPComponentFragment
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual

class UPSignInFragment(
    private val viewModel: SignInViewModel = SignInViewModel()
): UPComponentFragment() {

    @Composable
    override fun Content() {
        viewModel.navController = navController()
        val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            val idText by viewModel.idText.collectAsState()
            val passwordText by viewModel.passwordText.collectAsState()
            val signInUIState by viewModel.signInState.collectAsState()

            if (signInUIState is UIState.UIStateSuccess<String>) {
                viewModel.resetLoginState()
            }

            if (signInUIState is UIState.UIStateError<String>) {
                ShowSnackbar(
                    state = LocalSnackbarState.current,
                    visuals = UPSnackbarVisual(
                        message = signInUIState.error?.localizedMessage.value,
                        containerColor = Color.Red
                    )
                )
            }

            if (windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT) {
                SignInLogo(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }

            val credentialsContentWidthFraction = if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) 1f else 0.5f

            val properties = UPSignInCredentialsProperties(
                idText = idText,
                onEditId = viewModel::onEditId,
                passwordText = passwordText,
                onEditPassword = viewModel::onEditPassword,
                onClickSignIn = viewModel::performSignIn,
                contentWidthFraction = credentialsContentWidthFraction
            )

            SignInCredentials(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                properties = properties
            )
        }
    }
}