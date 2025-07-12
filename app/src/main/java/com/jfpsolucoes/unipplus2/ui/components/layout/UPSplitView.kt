package com.jfpsolucoes.unipplus2.ui.components.layout

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun <T> UPSplitView(
    modifier: Modifier = Modifier,
    navigation: ThreePaneScaffoldNavigator<T> = rememberSupportingPaneScaffoldNavigator<T>(),
    detailsContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {}
) = SupportingPaneScaffold(
    modifier = modifier,
    directive = navigation.scaffoldDirective,
    value = navigation.scaffoldValue,
    mainPane = {
        AnimatedPane { content.invoke() }
    },
    supportingPane = {
        AnimatedPane { detailsContent.invoke() }
    }
)