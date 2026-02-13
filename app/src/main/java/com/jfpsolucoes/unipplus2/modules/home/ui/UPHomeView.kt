@file:OptIn(ExperimentalMaterial3Api::class)

package com.jfpsolucoes.unipplus2.modules.home.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.utils.extensions.isWidthExpandedLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.isWidthExtraLargeLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.isWidthLargeLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.isWidthMediumLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.perform
import com.jfpsolucoes.unipplus2.core.utils.extensions.requestScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.extensions.showInterstitialAd
import com.jfpsolucoes.unipplus2.core.utils.extensions.stateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemDeeplink
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemType
import com.jfpsolucoes.unipplus2.modules.home.ui.components.UPHomeNavigationSuite
import com.jfpsolucoes.unipplus2.modules.secretary.features.ui.UPSecretaryView
import com.jfpsolucoes.unipplus2.modules.settings.ui.UPSettingsView
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.LocalNavigationLayoutType
import com.jfpsolucoes.unipplus2.ui.LocalSignInState
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundLow
import com.jfpsolucoes.unipplus2.ui.components.admob.ADBanner
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebView
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebViewSettings
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
private fun calculateNavigationTypeByWindowClass(): NavigationSuiteType {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return when {
        windowSizeClass.isWidthLargeLowerBound || windowSizeClass.isWidthExtraLargeLowerBound -> NavigationSuiteType.NavigationDrawer
        windowSizeClass.isWidthMediumLowerBound || windowSizeClass.isWidthExpandedLowerBound -> NavigationSuiteType.NavigationRail
        else -> NavigationSuiteType.None
    }
}

@SuppressLint("CoroutineCreationDuringComposition", "ContextCastToActivity")
@Composable
fun UPHomeView(
    viewModel: UPHomeViewModel = viewModel<UPHomeViewModelImpl>(),
    navController: NavHostController? = LocalNavController.current,
    navigationLayoutType: NavigationSuiteType = calculateNavigationTypeByWindowClass(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    activity: AppCompatActivity? = LocalContext.current as AppCompatActivity
) {
    activity?.requestScreenOrientation()

    val coroutineScope = rememberCoroutineScope()

    val systemsState by viewModel.systemsState.collectAsStateWithLifecycle()

    val selectedSystem by viewModel.systemSelected.collectAsStateWithLifecycle()

    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    val isAdEnabled by viewModel.adsEnabled.collectAsStateWithLifecycle()

    val shouldSignOut by viewModel.shouldSignOut.collectAsStateWithLifecycle()

    LaunchedEffect(shouldSignOut) {
        if (!shouldSignOut) { return@LaunchedEffect }
        navController?.popBackStack()
    }

    LaunchedEffect(isAdEnabled) {
        activity?.showInterstitialAd(isAdEnabled)
    }

    BackHandler {
        coroutineScope.perform(viewModel::onSignOut)
    }

    UPUIStateScaffold(
        state = systemsState,
        loadingContent = { UPLoadingView() },
        errorContent = { _, error ->
            UPErrorView(error = error) {
                viewModel.getSystems()
            }
        },
        floatingActionButton = {
            if (navigationLayoutType == NavigationSuiteType.None) {
                FloatingActionButton(
                    onClick = {
                        if (drawerState.isClosed) {
                            coroutineScope.perform(drawerState::open)
                        } else {
                            coroutineScope.perform(drawerState::close)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primaryBackgroundLow,
                    contentColor = Color.White
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_outline_menu_24),
                        contentDescription = ""
                    )
                }
            }
        },
        bottomBar = {
            if (isAdEnabled) {
                ADBanner(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = systemBarsPadding.calculateBottomPadding()
                        )
                )
            }
        },
        content = { padding, data ->
            UPHomeNavigationSuite(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                drawerState = drawerState,
                layoutType = navigationLayoutType,
                data = data,
                selectedSystem = selectedSystem,
                onSelectSystem = viewModel::onSelectedSystem,
                onClickExit = {
                    coroutineScope.perform(viewModel::onSignOut)
                },
                onClickOpenDrawer = { coroutineScope.perform(drawerState::open) }
            ) {
                CompositionLocalProvider(LocalNavigationLayoutType provides navigationLayoutType) {
                    coroutineScope.perform(drawerState::close)
                    selectedSystem?.let { system ->
                        if (system.type == UPSystemType.WEB) {
                            val settings = PortalWebViewSettings(url = system.url.value)
                            PortalWebView(
                                modifier = Modifier.padding(
                                    bottom = padding.calculateBottomPadding()
                                ),
                                webSettings = settings
                            )
                        }
                        when (system.deeplink) {
                            UPSystemDeeplink.SECRETARY -> {
                                UPSecretaryView(
                                    modifier = Modifier.padding(
                                        bottom = padding.calculateBottomPadding()
                                    )
                                )
                            }
                            UPSystemDeeplink.SETTINGS -> {
                                UPSettingsView(
                                    modifier = Modifier.padding(
                                        bottom = padding.calculateBottomPadding()
                                    ),
                                    title = system.description.value
                                )
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    )
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true)
@Composable
private fun UPHomeViewPreview() {
    UNIPPlus2Theme {
        UPHomeView(
            viewModel = UPHomeViewModelPreviewImpl(
                systemsState = UIState.UIStateSuccess(
                    UPHomeSystemsResponse(
                        feature = listOf(
                            UPSystem(id = 0, description = "teste", type = UPSystemType.FEATURE),
                            UPSystem(id = 1, description = "teste", type = UPSystemType.FEATURE),
                        ),
                        web = listOf(
                            UPSystem(id = 0, description = "teste", type = UPSystemType.FEATURE),
                            UPSystem(id = 1, description = "teste", type = UPSystemType.FEATURE),
                            UPSystem(id = 2, description = "teste", type = UPSystemType.FEATURE),
                            UPSystem(id = 3, description = "teste", type = UPSystemType.FEATURE)
                        ),
                    )
                ).stateFlow
            ),
            navController = null,
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            activity = null
        )
    }
}
