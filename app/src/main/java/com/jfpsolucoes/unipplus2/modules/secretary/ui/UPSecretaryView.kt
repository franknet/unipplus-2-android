package com.jfpsolucoes.unipplus2.modules.secretary.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.SupportingPane
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeature
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeaturesResponse
import com.jfpsolucoes.unipplus2.modules.secretary.ui.components.UPSecretaryFeatureDestination
import com.jfpsolucoes.unipplus2.modules.secretary.ui.dashboard.UPSecretaryDashboardView
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UPSecretaryView(
    modifier: Modifier = Modifier,
    viewModel: UPSecretaryViewModel = viewModel()
) {
    val featuresUIState by viewModel.featuresState.collectAsState()

    LaunchedEffect(Unit) {
        when (featuresUIState) {
            is UIState.UIStateNone -> { viewModel.fetchSecretaryFeatures()  }
            else -> { return@LaunchedEffect }
        }
    }

    UPUIStateScaffold(
        state = featuresUIState,
        loadingContent = { _ ->
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error) {
                viewModel.fetchSecretaryFeatures()
            }
        },
        content = { padding, data ->
            ContentView(data = data)
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun ContentView(
    modifier: Modifier = Modifier,
    data: UPSecretaryFeaturesResponse?
) {
    val coroutine = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator<UPSecretaryFeature>()
    val isDetailPaneHidden = navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Hidden

    if (!isDetailPaneHidden) {
        LaunchedEffect(Unit) {
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, data?.features?.first())
        }
    }

    ListDetailPaneScaffold(
        modifier = modifier,
        directive = navigator.scaffoldDirective,
        scaffoldState = navigator.scaffoldState,
        listPane = {
            UPSecretaryDashboardView(features = data?.features) { feature ->
                coroutine.launch {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, feature)
                }
            }
        },
        detailPane = {
            if (navigator.currentDestination?.pane == ListDetailPaneScaffoldRole.Detail) {
                UPSecretaryFeatureDestination(
                    navigator = navigator,
                    feature = navigator.currentDestination?.contentKey
                )
            }
        },
    )
}
