package com.jfpsolucoes.unipplus2.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.window.layout.FoldingFeature
import kotlinx.coroutines.flow.MutableStateFlow

val LocalNavController = compositionLocalOf<NavHostController> { error("") }
val LocalFoldingFeatures = compositionLocalOf<List<FoldingFeature>?> { error("") }
val LocalNavigationLayoutType = compositionLocalOf<NavigationSuiteType> { error("") }

val LocalSignInState = compositionLocalOf<MutableStateFlow<Boolean>> { error("") }

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
val LocalThreePaneScaffoldIntNavigator = compositionLocalOf<ThreePaneScaffoldNavigator<Int>> { error("") }
