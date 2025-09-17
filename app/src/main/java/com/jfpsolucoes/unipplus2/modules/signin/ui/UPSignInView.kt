package com.jfpsolucoes.unipplus2.modules.signin.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInCredentialsProperties
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInCredentials
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInLogo
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun UPSignInView(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = viewModel()
) {
    val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val navController = LocalNavController.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(viewModel.snackbarState) {
                Snackbar(it)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            val idText by viewModel.idText.collectAsState()
            val passwordText by viewModel.passwordText.collectAsState()
            val signInUIState by viewModel.signInState.collectAsState()

            if (signInUIState is UIState.UIStateSuccess) {
                viewModel.resetLoginState()
                navController.navigate(route = HOME_NAVIGATION_ROUTE)
            }

            if (signInUIState is UIState.UIStateError) {
                rememberCoroutineScope().launch {
                    viewModel.snackbarState.showSnackbar(message = signInUIState.error?.localizedMessage.value)
                }
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
                isLoading = signInUIState is UIState.UIStateLoading,
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