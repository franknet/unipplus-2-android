package com.jfpsolucoes.unipplus2.modules.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.utils.extensions.rememberState
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemType
import com.jfpsolucoes.unipplus2.modules.home.ui.components.UPHomeFeatureDestination
import com.jfpsolucoes.unipplus2.modules.home.ui.components.UPHomeNavigationSuite
import com.jfpsolucoes.unipplus2.modules.home.ui.components.suiteLayoutTypeFromAdaptiveInfo
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.LocalNavigationLayoutType
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebView
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebViewSettings

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UPHomeView(
    modifier: Modifier = Modifier,
    viewModel: UPHomeViewModel = viewModel()
) {
    var hasFetchedData by rememberSaveable { mutableStateOf(false) }
    val systemsState by viewModel.systemsState.collectAsState()

    LaunchedEffect(Unit) {
        if (hasFetchedData) return@LaunchedEffect
        viewModel.fetchSystems()
        hasFetchedData = true
    }

    UPUIStateScaffold(
        modifier = modifier,
        state = systemsState,
        loadingContent = {
            UPLoadingView()
                         },
        errorContent = { _, error ->
            UPErrorView(error = error) { viewModel.fetchSystems() }
        },
        content = { _, data ->
            SuccessContent(systems = data)
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
private fun SuccessContent(
    systems: UPHomeSystemsResponse? = null
) {
    var selectedSystem by systems?.feature?.first().rememberState
    val navController = LocalNavController.current
    var navigationLayoutType = suiteLayoutTypeFromAdaptiveInfo()

    UPHomeNavigationSuite(
        layoutType = navigationLayoutType,
        systems = systems,
        onSelectSystem = { selectedSystem = it },
        onClickExit = { navController.popBackStack() }
    ) {
        CompositionLocalProvider(LocalNavigationLayoutType provides navigationLayoutType) {
            when (selectedSystem?.type) {
                UPSystemType.FEATURE -> {
                    UPHomeFeatureDestination(system = selectedSystem)
                }
                UPSystemType.WEB -> {
                    val settings = PortalWebViewSettings(url = selectedSystem?.url ?: "")
                    PortalWebView(webSettings = settings)
                }
                else -> {}
            }
        }
    }
}