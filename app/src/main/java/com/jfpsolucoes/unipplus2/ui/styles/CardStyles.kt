package com.jfpsolucoes.unipplus2.ui.styles

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable

val defaultCardColors: CardColors
    @Composable get() = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        contentColor = MaterialTheme.colorScheme.onSurface,
    )

val CardColorsPrimary: CardColors
    @Composable get() = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )

val secondCardColors: CardColors
    @Composable get() = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceBright,
        contentColor = contentColorFor(MaterialTheme.colorScheme.surfaceBright),

        disabledContainerColor = MaterialTheme.colorScheme.surfaceBright.copy(0.5f),
        disabledContentColor = contentColorFor(MaterialTheme.colorScheme.surfaceBright).copy(0.5f),
    )