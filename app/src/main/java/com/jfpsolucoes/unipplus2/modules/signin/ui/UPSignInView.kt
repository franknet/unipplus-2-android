@file:OptIn(ExperimentalMaterial3Api::class)

package com.jfpsolucoes.unipplus2.modules.signin.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowSizeClass
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.ScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.extensions.isHeightMediumLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.requestScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.extensions.stateFlow
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInCredentials
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInLogo
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundHigh
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundLow
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun UPSignInView(
    modifier: Modifier = Modifier,
    viewModel: UPSignInViewModel = viewModel<SignInViewModelImpl>(),
    navController: NavHostController? = LocalNavController.current,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    activity: AppCompatActivity? = com.jfpsolucoes.unipplus2.core.utils.extensions.activity
) {
    val userProfileState by viewModel.userProfileState.collectAsStateWithLifecycle()
    val signInUIState by viewModel.signInState.collectAsStateWithLifecycle()

    val rgText by viewModel.rgText.collectAsStateWithLifecycle()
    val passwordText by viewModel.passwordText.collectAsStateWithLifecycle()
    val showPasswordField by viewModel.showPasswordField.collectAsStateWithLifecycle()
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    if (windowSizeClass.isHeightMediumLowerBound) {
        activity?.requestScreenOrientation(ScreenOrientation.PORTRAIT)
    }

    if (signInUIState.success) {
        viewModel.resetLoginState()
        viewModel.resetBiometricState()
        navController?.navigate(route = HOME_NAVIGATION_ROUTE)
    }

    if (signInUIState.failure) {
        if (!showPasswordField) {
            viewModel.onChangeAutoSignIn(false)
        }
        viewModel.showSnackbar(signInUIState.error?.message ?: "")
    }

    LaunchedEffect(settings.autoSignIn) {
        if (settings.autoSignIn) {
            viewModel.performSignIn(null)
        }
        if (settings.biometricEnabled) {
            viewModel.performSignIn(activity)
        }
    }

    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryBackgroundLow,
                        MaterialTheme.colorScheme.primaryBackgroundHigh,
                    )
                )
            )
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInLogo(modifier = Modifier
            .weight(1f)
            .fillMaxWidth())

        SignInCredentials(
            modifier = Modifier.weight(1f),
            userProfile = userProfileState.data,
            settings = settings,
            onLoading = signInUIState is UIState.UIStateLoading,
            raText = rgText,
            onEditRa = viewModel::onEditRg,
            passwordText = passwordText,
            showPasswordField = showPasswordField,
            onEditPassword = viewModel::onEditPassword,
            onAutoSignInChange = viewModel::onChangeAutoSignIn,
            onClickSignIn = { viewModel.performSignIn(activity) }
        )
    }
}