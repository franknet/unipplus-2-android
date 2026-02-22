package com.jfpsolucoes.unipplus2.ui.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val UPSegmentedButtonColors: SegmentedButtonColors
    @Composable
    get() = SegmentedButtonDefaults.colors(
        inactiveContainerColor = MaterialTheme.colorScheme.surfaceBright,
        activeContentColor = MaterialTheme.colorScheme.primary
    )