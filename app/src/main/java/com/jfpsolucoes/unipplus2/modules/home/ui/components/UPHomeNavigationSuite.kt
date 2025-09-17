package com.jfpsolucoes.unipplus2.modules.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem

@Composable
fun UPHomeNavigationSuite(
    layoutType: NavigationSuiteType = suiteLayoutTypeFromAdaptiveInfo(),
    systems: UPHomeSystemsResponse? = null,
    onSelectSystem: (UPSystem) -> Unit,
    onClickSystems: (List<UPSystem>?) -> Unit = {},
    onClickSettings: () -> Unit = {},
    onClickExit: () -> Unit = {},
    content: @Composable () -> Unit
) {
    NavigationSuiteScaffoldLayout(
        layoutType = layoutType,
        navigationSuite = {
            when (layoutType) {
                NavigationSuiteType.NavigationRail -> {
                    UPHomeNavigationRail(
                        data = systems,
                        onSelectFeature = onSelectSystem,
                        onClickSystems = onClickSystems,
                        onClickSettings = onClickSettings,
                        onClickExit = onClickExit
                    )
                }
                NavigationSuiteType.NavigationDrawer -> {
                    UPHomeNavigationDrawer(
                        data = systems,
                        onSelectSystem = onSelectSystem,
                        onExitClick = onClickExit
                    )
                }
                else -> {
                    UPHomeNavigationBar(data = systems)
                }
            }
        }
    ) {
        content()
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun suiteLayoutTypeFromAdaptiveInfo(): NavigationSuiteType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
    currentWindowAdaptiveInfo()
)