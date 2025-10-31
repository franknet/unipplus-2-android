package com.jfpsolucoes.unipplus2.modules.home.ui

import android.annotation.SuppressLint
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.utils.extensions.ShowInterstitialAd
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.isWidthExpandedLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.isWidthExtraLargeLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.isWidthLargeLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.isWidthMediumLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.perform
import com.jfpsolucoes.unipplus2.core.utils.extensions.requestScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemDeeplink
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemType
import com.jfpsolucoes.unipplus2.modules.home.ui.components.UPHomeNavigationSuite
import com.jfpsolucoes.unipplus2.modules.home.ui.components.suiteLayoutTypeFromAdaptiveInfo
import com.jfpsolucoes.unipplus2.modules.secretary.ui.UPSecretaryView
import com.jfpsolucoes.unipplus2.modules.settings.ui.UPSettingsView
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.LocalNavigationLayoutType
import com.jfpsolucoes.unipplus2.ui.components.appshare.ShareAppDialog
import com.jfpsolucoes.unipplus2.ui.components.dialogs.UPBiometricAlertDialog
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebView
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebViewSettings

@Composable
private fun calculateNavigationTypeByWindowClass(): NavigationSuiteType {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return when {
        windowSizeClass.isWidthLargeLowerBound || windowSizeClass.isWidthExtraLargeLowerBound -> NavigationSuiteType.NavigationDrawer
        windowSizeClass.isWidthMediumLowerBound || windowSizeClass.isWidthExpandedLowerBound -> NavigationSuiteType.NavigationRail
        else -> suiteLayoutTypeFromAdaptiveInfo()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UPHomeView(
    viewModel: UPHomeViewModel = viewModel(),
) {
    val systemsState by viewModel.systems.collectAsState()

    activity.requestScreenOrientation()

    ShareAppDialog()

    ShowInterstitialAd()

    UPUIStateScaffold(
        state = systemsState,
        loadingContent = { UPLoadingView() },
        errorContent = { _, error ->
            UPErrorView(error = error) {
                viewModel.getSystems()
            }
        },
        content = { _, data ->
            SuccessContent(
                data = data,
                viewModel = viewModel
            )
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
private fun SuccessContent(
    data: UPHomeSystemsResponse? = null,
    viewModel: UPHomeViewModel
) {
    val navController = LocalNavController.current
    val coroutineScope = rememberCoroutineScope()
    val navigationLayoutType = calculateNavigationTypeByWindowClass()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val selectedSystem by viewModel.systemSelected.collectAsStateWithLifecycle()
    val biometricDialogEnabled by viewModel.biometricDialogEnabled.collectAsStateWithLifecycle()

    if (biometricDialogEnabled) {
        UPBiometricAlertDialog(
            onClickOk = viewModel::onClickOKBiometricDialog,
            onClickCancel = viewModel::updateSettings
        )
    }

    UPHomeNavigationSuite(
        drawerState = drawerState,
        layoutType = navigationLayoutType,
        data = data,
        selectedSystem = selectedSystem,
        onSelectSystem = viewModel::onSelectedSystem,
        onClickExit = navController::popBackStack,
        onClickOpenDrawer = { coroutineScope.perform(drawerState::open) }
    ) {
        Surface {
            CompositionLocalProvider(LocalNavigationLayoutType provides navigationLayoutType) {
                coroutineScope.perform(drawerState::close)
                selectedSystem?.let { system ->
                    if (system.type == UPSystemType.WEB) {
                        val settings = PortalWebViewSettings(url = system.url.value)
                        PortalWebView(webSettings = settings)
                    }
                    else {
                        when (system.deeplink) {
                            UPSystemDeeplink.SECRETARY -> {
                                UPSecretaryView()
                            }
                            UPSystemDeeplink.SETTINGS -> {
                                UPSettingsView(title = system.description.value)
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}