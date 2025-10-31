package com.jfpsolucoes.unipplus2.modules.secretary.ui

import android.annotation.SuppressLint
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.utils.extensions.isMainPaneExpanded
import com.jfpsolucoes.unipplus2.core.utils.extensions.isSupportingPaneExpanded
import com.jfpsolucoes.unipplus2.core.utils.extensions.perform
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.SECRETARY_FINANCIAL_DEEPLINK
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.SECRETARY_STUDENT_RECORDS_DEEPLINK
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeature
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeaturesResponse
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.ui.UPSecretaryStudentRecordsView
import com.jfpsolucoes.unipplus2.modules.secretary.ui.dashboard.UPSecretaryDashboardView
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
    data: UPSecretaryFeaturesResponse
) {
    val coroutineScope = rememberCoroutineScope()
    val navigator = rememberSupportingPaneScaffoldNavigator<UPSecretaryFeature>()

    if (navigator.scaffoldValue.isSupportingPaneExpanded) {
        LaunchedEffect(Unit) {
            navigator.navigateTo(SupportingPaneScaffoldRole.Supporting, data.features?.first())
        }
    }

    SupportingPaneScaffold(
        directive = navigator.scaffoldDirective,
        scaffoldState = navigator.scaffoldState,
        mainPane = {
            UPSecretaryDashboardView(features = data.features) { feature ->
                coroutineScope.launch {
                    navigator.navigateTo(SupportingPaneScaffoldRole.Supporting, feature)
                }
            }
        },
        supportingPane = {
            if (navigator.currentDestination?.pane == SupportingPaneScaffoldRole.Supporting) {
                navigator.currentDestination?.contentKey?.let { system ->
                    when (system.deeplink) {
                        SECRETARY_STUDENT_RECORDS_DEEPLINK -> UPSecretaryStudentRecordsView(
                            feature = system,
                            navigationButtonEnabled = !navigator.scaffoldValue.isMainPaneExpanded,
                            onClickBack = { coroutineScope.perform(navigator::navigateBack) }
                        )
                        SECRETARY_FINANCIAL_DEEPLINK -> {}
                        else -> {}
                    }
                }
            }
        },
    )
}