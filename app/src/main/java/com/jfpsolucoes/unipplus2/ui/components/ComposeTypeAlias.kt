@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.jfpsolucoes.unipplus2.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable

typealias ComposeLambda = @Composable () -> Unit
typealias RowScopeLambda = @Composable RowScope.() -> Unit
typealias ColumnScopeLambda = @Composable ColumnScope.() -> Unit
typealias ThreePaneScaffoldScopeLambda = @Composable ThreePaneScaffoldScope.() -> Unit
typealias NavigationSuiteScopeLambda = NavigationSuiteScope.() -> Unit
typealias LazyListScopeLambda = LazyListScope.() -> Unit