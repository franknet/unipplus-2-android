package com.jfpsolucoes.unipplus2.modules.signin.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.isHeightMediumLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInCredentials
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInLogo
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun UPSignInView(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = viewModel()
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val navController = LocalNavController.current
    val rgText by viewModel.rgText.collectAsState()
    val passwordText by viewModel.passwordText.collectAsState()
    var passwordTextVisible by true.saveableMutableState
    val signInUIState by viewModel.signInState.collectAsState()
    val biometricEnabled by viewModel.biometricEnabled.collectAsStateWithLifecycle()

    if (biometricEnabled) {
        viewModel.requestBiometricAuthentication(activity)
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(viewModel.snackbarState) {
                Snackbar(it)
            }
        }
    ) {
        Column(Modifier.safeDrawingPadding(), horizontalAlignment = Alignment.CenterHorizontally) {
            if (signInUIState is UIState.UIStateSuccess) {
                viewModel.resetLoginState()
                navController.navigate(route = HOME_NAVIGATION_ROUTE)
            }

            if (signInUIState is UIState.UIStateError) {
                val snackbarVisuals = UPSnackbarVisual(
                    message = signInUIState.error?.localizedMessage.value,
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
                rememberCoroutineScope().launch {
                    val result = viewModel.snackbarState.showSnackbar(snackbarVisuals)
                    if (result == SnackbarResult.Dismissed) {
                        viewModel.resetLoginState()
                    }
                }
            }

            if (windowSizeClass.isHeightMediumLowerBound) {
                SignInLogo(modifier = Modifier.weight(1f).fillMaxWidth())
            }

            SignInCredentials(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                onLoading = signInUIState is UIState.UIStateLoading,
                raText = rgText,
                onEditRa = viewModel::onEditRg,
                passwordText = passwordText,
                passwordTextVisible = passwordTextVisible,
                onEditPassword = viewModel::onEditPassword,
                onClickSignIn = viewModel::performSignIn,
            )
        }
    }
}