package com.jfpsolucoes.unipplus2.modules.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem

@Composable
fun UPHomeNavigationSuite(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    layoutType: NavigationSuiteType = suiteLayoutTypeFromAdaptiveInfo(),
    data: UPHomeSystemsResponse? = null,
    onSelectSystem: (UPSystem) -> Unit,
    onClickSystems: (List<UPSystem>?) -> Unit = {},
    onClickExit: () -> Unit = {},
    onClickOpenDrawer: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            UPHomeNavigationDrawer(
                data = data,
                onSelectSystem = onSelectSystem,
                onClickExit = onClickExit
            )
        },
        gesturesEnabled = layoutType == NavigationSuiteType.NavigationBar,
    ) {
        NavigationSuiteScaffoldLayout(
            layoutType = layoutType,
            navigationSuite = {
                when (layoutType) {
                    NavigationSuiteType.NavigationRail -> {
                        UPHomeNavigationRail(
                            data = data,
                            onSelectSystem = onSelectSystem,
                            onClickSystems = onClickSystems,
                            onClickExit = onClickExit
                        )
                    }
                    NavigationSuiteType.NavigationDrawer -> {
                        UPHomeNavigationDrawer(
                            data = data,
                            onSelectSystem = onSelectSystem,
                            onClickExit = onClickExit
                        )
                    }
                    else -> {
                        UPHomeNavigationBar(
                            data = data,
                            onClickMenu = onClickOpenDrawer
                        )
                    }
                }
            }
        ) {
            content()
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun suiteLayoutTypeFromAdaptiveInfo(): NavigationSuiteType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
    currentWindowAdaptiveInfo()
)