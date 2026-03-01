package com.jfpsolucoes.unipplus2.ui.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TopBarColorsOnBackground: TopAppBarColors
    @Composable get() = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        actionIconContentColor = MaterialTheme.colorScheme.onBackground,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        subtitleContentColor = MaterialTheme.colorScheme.onBackground,
        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
        scrolledContainerColor = MaterialTheme.colorScheme.onBackground
    )