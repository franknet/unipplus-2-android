package com.jfpsolucoes.unipplus2.core.utils.extensions

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldValue

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
val ThreePaneScaffoldValue.isSupportingPaneExpanded: Boolean
    get() = this[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Expanded

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
val ThreePaneScaffoldValue.isMainPaneExpanded: Boolean
    get() = this[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Expanded