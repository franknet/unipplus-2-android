package com.jfpsolucoes.unipplus2.modules.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UPHomeNavigationSuite(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    layoutType: NavigationSuiteType = suiteLayoutTypeFromAdaptiveInfo(),
    data: UPHomeSystemsResponse? = null,
    selectedSystem: UPSystem?,
    onSelectSystem: (UPSystem) -> Unit,
    onClickExit: () -> Unit = {},
    onClickOpenDrawer: () -> Unit,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            UPHomeNavigationDrawer(
                modifier = modifier,
                data = data,
                selectedSystem = selectedSystem,
                onSelectSystem = onSelectSystem,
                onClickExit = onClickExit
            )
        },
        gesturesEnabled = layoutType != NavigationSuiteType.NavigationDrawer,
    ) {
        NavigationSuiteScaffoldLayout(
            layoutType = layoutType,
            navigationSuite = {
                when (layoutType) {
                    NavigationSuiteType.NavigationRail -> {
                        UPHomeNavigationRail(
                            modifier = modifier,
                            data = data,
                            selectedSystem = selectedSystem,
                            onSelectSystem = onSelectSystem,
                            onClickOpenMenu = onClickOpenDrawer
                        )
                    }
                    NavigationSuiteType.NavigationDrawer -> {
                        UPHomeNavigationDrawer(
                            modifier = modifier,
                            data = data,
                            selectedSystem = selectedSystem,
                            onSelectSystem = onSelectSystem,
                            onClickExit = onClickExit
                        )
                    }
                    else -> { }
                }
            }
        ) {
            Scaffold(
                bottomBar = {
                    bottomBar.invoke()
                },
                containerColor = Color.Transparent
            ) { parentPadding ->
                content(parentPadding)
            }
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun suiteLayoutTypeFromAdaptiveInfo(): NavigationSuiteType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
    currentWindowAdaptiveInfo()
)