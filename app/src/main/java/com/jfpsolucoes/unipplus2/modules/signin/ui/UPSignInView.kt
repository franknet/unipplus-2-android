@file:OptIn(ExperimentalMaterial3Api::class)

package com.jfpsolucoes.unipplus2.modules.signin.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.compose.RememberLaunchedEffect
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInCredentials
import com.jfpsolucoes.unipplus2.modules.signin.ui.components.SignInLogo
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.colors.primaryHighContrast
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.lastOrNull

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
    val password by viewModel.password.collectAsStateWithLifecycle()
    val showPasswordField by viewModel.passwordFieldVisible.collectAsStateWithLifecycle()
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val screenUIState by viewModel.screenUIState.collectAsStateWithLifecycle()
    val signInUIState by viewModel.singInUIState.collectAsStateWithLifecycle()
    val snackBarMessage by viewModel.snackBarMessage.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        navController?.addOnDestinationChangedListener { _, _, _ ->
            viewModel.resetSingInState()
        }
    }

    RememberLaunchedEffect {
        viewModel.trackScreenView()
    }

    LaunchedEffect(settings) {
        if (settings.biometricEnabled) {
            UPBiometricManagerImpl.authenticate(
                activity,
                onSuccess = viewModel::performSignIn,
                onFailed = {},
                onError = { _, _ ->
                    viewModel.resetAndShowPasswordField()
                },
                onCancel = {
                    viewModel.resetAndShowPasswordField()
                }
            )
        }
    }

    LaunchedEffect(signInUIState) {
        if (signInUIState.success) {
            navController?.navigate(HOME_NAVIGATION_ROUTE)
        }
    }

    LaunchedEffect(snackBarMessage) {
        if (snackBarMessage.isNullOrEmpty()) { return@LaunchedEffect }
        snackbarState.showSnackbar(UPSnackbarVisual(
            message = snackBarMessage.orEmpty()
        ))
    }

    UPUIStateScaffold(
        modifier = modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primaryHighContrast,
                )
            )
        ),
        state = screenUIState,
        snackbarHost = {
            SnackbarHost(snackbarState)
        },
        loadingContent = { _ ->
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error)
        },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) { _, userProfile ->
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
                        userName = userProfile.name,
                        userCourse = userProfile.academic?.course?.name,
                        onLoading = signInUIState.loading,
                        raText = rgText,
                        onEditRa = viewModel::updateRgText,
                        passwordText = password,
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