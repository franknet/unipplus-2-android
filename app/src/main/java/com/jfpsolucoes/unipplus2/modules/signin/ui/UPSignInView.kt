@file:OptIn(ExperimentalMaterial3Api::class)

package com.jfpsolucoes.unipplus2.modules.signin.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowSizeClass
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.extensions.ScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.isHeightMediumLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.requestScreenOrientation
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInCredentials
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInLogo
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundHigh
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundLow
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun UPSignInView(
    modifier: Modifier = Modifier,
    viewModel: UPSignInViewModel = viewModel(),
    navController: NavHostController? = LocalNavController.current,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    snackbarState: SnackbarHostState = SnackbarHostState(),
    biometricManager: UPBiometricManager? = UPBiometricManagerImpl,
) {
    val activity = activity
    val credentials by viewModel.credentials.collectAsStateWithLifecycle()
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val signInUIState by viewModel.singInUIState.collectAsStateWithLifecycle()

    LaunchedEffect(settings) {
        if (settings.autoSignIn && !settings.signedIn) {
            viewModel.performSignIn()
        }
        if (settings.biometricEnabled && !settings.signedIn) {
            biometricManager?.authenticate(
                activity,
                onSuccess = {
                    viewModel.performSignIn()
                },
                onFailed = { },
                onError = { code, message -> },
                onCancel = { }
            )
        }
    }

    LaunchedEffect(signInUIState) {
        if (signInUIState.success) {
            viewModel.resetSingInState()
            navController?.navigate(HOME_NAVIGATION_ROUTE)
        }
        if (signInUIState.failure) {
            snackbarState.showSnackbar(UPSnackbarVisual(
                message = signInUIState.error?.message.orEmpty()
            ))
        }
    }

    Scaffold(
        modifier = modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primaryBackgroundLow,
                    MaterialTheme.colorScheme.primaryBackgroundHigh,
                )
            )
        ),
        snackbarHost = {
            SnackbarHost(snackbarState)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInLogo(modifier = Modifier
                .weight(1f)
                .fillMaxWidth())

            SignInCredentials(
                modifier = Modifier.weight(1f),
                userName = userProfile?.name,
                userCourse = userProfile?.academic?.course?.name,
                onLoading = signInUIState.loading,
                raText = credentials.rg,
                onEditRa = {
                    viewModel.updateCredentials(credentials.copy(rg = it))
                },
                passwordText = credentials.password,
                onEditPassword = {
                    viewModel.updateCredentials(credentials.copy(password = it))
                },
                showPasswordField = !settings.autoSignIn.or(settings.biometricEnabled),
                autoSignChecked = settings.autoSignIn,
                onAutoSignInChange = {
                    viewModel.updateSettings(settings.copy(autoSignIn = it))
                },
                onClickSignIn = {
                    viewModel.performSignIn()
                }
            )
        }
    }
}