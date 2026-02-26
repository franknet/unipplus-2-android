package com.jfpsolucoes.unipplus2.ui.styles

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable

val defaultCardColors: CardColors
    @Composable get() = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor = contentColorFor(MaterialTheme.colorScheme.surfaceContainerHigh),
    )

val CardColorsPrimary: CardColors
    @Composable get() = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = contentColorFor(MaterialTheme.colorScheme.primaryContainer)
    )

val secondCardColors: CardColors
    @Composable get() = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor = contentColorFor(MaterialTheme.colorScheme.surfaceContainerHigh),

        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh.copy(0.75f),
        disabledContentColor = contentColorFor(MaterialTheme.colorScheme.surfaceContainerHigh.copy(0.75f)),
    )