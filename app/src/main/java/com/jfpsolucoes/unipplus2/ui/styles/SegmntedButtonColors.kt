package com.jfpsolucoes.unipplus2.ui.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable

val SegmentedButtonColorsPrimaryHigh: SegmentedButtonColors
    @Composable
    get() = SegmentedButtonDefaults.colors(
        activeContainerColor = MaterialTheme.colorScheme.primaryFixed,
        inactiveContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
    )