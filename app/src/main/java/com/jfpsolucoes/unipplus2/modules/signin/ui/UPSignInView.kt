@file:OptIn(ExperimentalMaterial3Api::class)

package com.jfpsolucoes.unipplus2.modules.signin.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.showInterstitialAd
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInCredentials
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInLogo
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundHigh
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundLow
import com.jfpsolucoes.unipplus2.ui.colors.primaryHighContrast
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun UPSignInView(
    modifier: Modifier = Modifier,
    viewModel: UPSignInViewModel = viewModel(),
    navController: NavHostController? = LocalNavController.current,
    snackbarState: SnackbarHostState = SnackbarHostState(),
) {
    val activity = activity
    val rgText by viewModel.rgText.collectAsStateWithLifecycle()
    val passwordText by viewModel.passwordText.collectAsStateWithLifecycle()
    val showPasswordField by viewModel.showPasswordField.collectAsStateWithLifecycle()
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val signInUIState by viewModel.singInUIState.collectAsStateWithLifecycle()

    var loading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(settings) {
        if (settings.autoSignIn) {
            viewModel.performSignIn()
        }
        if (settings.biometricEnabled) {
            viewModel.performBiometricAuthentication(activity)
        }
    }

    LaunchedEffect(signInUIState) {
        loading = signInUIState.loading
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
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primaryHighContrast,
                )
            )
        ),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        snackbarHost = {
            SnackbarHost(snackbarState)
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier.safeGesturesPadding()
        ) {
            val itemHigh = (maxHeight / 2)

            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SignInLogo(modifier = Modifier
                        .height(itemHigh)
                        .fillMaxWidth())
                }

                item {
                    SignInCredentials(
                        modifier = Modifier.height(itemHigh),
                        userName = userProfile?.name,
                        userCourse = userProfile?.academic?.course?.name,
                        onLoading = loading,
                        raText = rgText,
                        onEditRa = viewModel::updateRg,
                        passwordText = passwordText,
                        onEditPassword = viewModel::updatePassword,
                        showPasswordField = showPasswordField,
                        autoSignChecked = settings.autoSignIn,
                        onAutoSignInChange = {
                            viewModel.updateSettings(settings.copy(autoSignIn = it))
                        },
                        onClickSignIn = viewModel::performSignIn
                    )
                }
            }
        }
    }
}