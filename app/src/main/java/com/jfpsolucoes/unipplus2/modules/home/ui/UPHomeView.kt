package com.jfpsolucoes.unipplus2.modules.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.ui.components.UPHomeFeatureDestination
import com.jfpsolucoes.unipplus2.modules.home.ui.components.UPHomeNavigationSuite
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UPHomeView(
    modifier: Modifier = Modifier,
    viewModel: UPHomeViewModel = viewModel()
) {
    val systemsState by viewModel.systemsState.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchSystems()
    }

    UPUIStateScaffold(
        modifier = modifier.fillMaxSize(),
        state = systemsState,
        loadingContent = {
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error) {
                viewModel.fetchSystems()
            }
        },
        content = { _, data ->
            SuccessContent(
                systems = data
            )
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
private fun SuccessContent(
    systems: UPHomeSystemsResponse? = null
) {
    var selectedSystem by remember {
        mutableStateOf(systems?.feature?.first())
    }
    val navController = LocalNavController.current

    UPHomeNavigationSuite(
        systems = systems,
        onSelectSystem = {
            selectedSystem = it
        },
        onClickExit = {
            navController.popBackStack()
        }
    ) {
        UPHomeFeatureDestination(
            modifier = Modifier.fillMaxSize(),
            system = selectedSystem
        )
    }
}