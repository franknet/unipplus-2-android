package com.jfpsolucoes.unipplus2.ui.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable

val SwitchColorsPrimaryFixed: SwitchColors
    @Composable get() = SwitchDefaults.colors(
        checkedTrackColor = MaterialTheme.colorScheme.primaryFixed
    )