package com.jfpsolucoes.unipplus2.modules.signin.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.core.common.model.UPAppInfo
import com.jfpsolucoes.unipplus2.core.database.SHARED_KEY_APP_INFO
import com.jfpsolucoes.unipplus2.core.database.SharedPreferencesManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInCredentialsProperties
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

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(viewModel.snackbarState) {
                Snackbar(it)
            }
        }
    ) {
        Column(Modifier.safeDrawingPadding(), horizontalAlignment = Alignment.CenterHorizontally) {
            val idText by viewModel.idText.collectAsState()
            val passwordText by viewModel.passwordText.collectAsState()
            val signInUIState by viewModel.signInState.collectAsState()

            if (signInUIState is UIState.UIStateSuccess) {
                viewModel.resetLoginState()
                val session = UPAppInfo(signInUIState.data)
                SharedPreferencesManager.saveObject(SHARED_KEY_APP_INFO, session)
                navController.navigate(route = HOME_NAVIGATION_ROUTE)
            }

            if (signInUIState is UIState.UIStateError) {
                val snackbarVisuals = UPSnackbarVisual(
                    message = signInUIState.error?.localizedMessage.value,
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
                rememberCoroutineScope().launch {
                    viewModel.snackbarState.showSnackbar(snackbarVisuals)
                }
            }

            if (windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)) {
                SignInLogo(modifier = Modifier.weight(1f).fillMaxWidth())
            }

            SignInCredentials(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                onLoading = signInUIState is UIState.UIStateLoading,
                raText = idText,
                onEditRa = viewModel::onEditId,
                passwordText = passwordText,
                onEditPassword = viewModel::onEditPassword,
                onClickSignIn = viewModel::performSignIn,
            )
        }
    }
}