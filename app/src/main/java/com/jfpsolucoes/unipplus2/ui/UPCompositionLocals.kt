package com.jfpsolucoes.unipplus2.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.window.layout.FoldingFeature

val LocalNavController = compositionLocalOf<NavHostController> { error("") }
val LocalFoldingFeatures = compositionLocalOf<List<FoldingFeature>?> { error("") }

val LocalNavigationLayoutType = compositionLocalOf<NavigationSuiteType> { error("") }
