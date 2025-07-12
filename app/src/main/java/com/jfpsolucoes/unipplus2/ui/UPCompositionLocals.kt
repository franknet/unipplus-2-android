package com.jfpsolucoes.unipplus2.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.window.layout.FoldingFeature

val LocalNavController = compositionLocalOf<NavHostController> { error("") }
val LocalSnackbarState = compositionLocalOf<SnackbarHostState> { error("") }
val LocalFoldingFeatures = compositionLocalOf<List<FoldingFeature>?> { error("") }