@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.jfpsolucoes.unipplus2.modules.home.ui.HomeViewModel

private typealias SupportingPaneNavigation = ThreePaneScaffoldNavigator<Nothing>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: HomeViewModel? = null
) {
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())

    NavigationSuiteScaffoldLayout(
        navigationSuite = {
            
        },
        layoutType
    ) {
        Box(
            modifier = Modifier
                .background(Color.Red)
                .fillMaxSize()
        )
    }

}

@Preview(showSystemUi = true, device = Devices.TABLET)
@Composable
private fun HomeViewPreview() {
    HomeView()
}

@Composable
fun HomeRailMenuView() {
    NavigationSuiteScaffold()
}
