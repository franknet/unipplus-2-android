package com.jfpsolucoes.unipplus2.modules.signin.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.core.utils.extensions.ScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.isHeightMediumLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.requestScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInCredentials
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInLogo
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun UPSignInView(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = viewModel()
) {
    val activity = activity
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val navController = LocalNavController.current

    val userProfileState by viewModel.userProfileState.collectAsStateWithLifecycle()
    val signInUIState by viewModel.signInState.collectAsStateWithLifecycle()

    val rgText by viewModel.rgText.collectAsStateWithLifecycle()
    val passwordText by viewModel.passwordText.collectAsStateWithLifecycle()
    val biometricEnabled by viewModel.biometricEnabled.collectAsStateWithLifecycle()
    val biometricError by viewModel.biometricError.collectAsStateWithLifecycle()

    if (windowSizeClass.isHeightMediumLowerBound) {
        activity.requestScreenOrientation(ScreenOrientation.PORTRAIT)
    }

    UPUIStateScaffold(
        state = userProfileState,
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(viewModel.snackbarState) {
                Snackbar(it)
            }
        },
        loadingContent = {
            UPLoadingView()
        },
        errorContent = { padding, error ->
            UPErrorView(error = error)
        }
    ) { padding, userProfile ->
        Column(Modifier.safeDrawingPadding(), horizontalAlignment = Alignment.CenterHorizontally) {
            if (signInUIState is UIState.UIStateSuccess) {
                viewModel.resetLoginState()
                viewModel.resetBiometricState()
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

            SignInLogo(modifier = Modifier
                .weight(1f)
                .fillMaxWidth())

            SignInCredentials(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onLoading = signInUIState is UIState.UIStateLoading,
                userProfile = userProfile,
                raText = rgText,
                onEditRa = viewModel::onEditRg,
                passwordText = passwordText,
                passwordSupportingText = biometricError,
                passwordFieldVisible = !biometricEnabled,
                onEditPassword = viewModel::onEditPassword,
                onClickSignIn = { viewModel.performSignIn(activity) }
            )
        }
    }
}