package com.jfpsolucoes.unipplus2.ui.styles

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundHighContrast
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundLow

val defaultButtonColors: ButtonColors
    @Composable get() = ButtonDefaults.buttonColors(
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.primaryBackgroundLow
    )

val buttonPrimaryHighContrastColors: ButtonColors
    @Composable get() = ButtonDefaults.buttonColors(
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.primaryBackgroundHighContrast
    )

val ButtonColorsPrimaryFixed: ButtonColors
    @Composable get() = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.onPrimaryFixed,
        disabledContentColor = MaterialTheme.colorScheme.onPrimaryFixed,
        containerColor = MaterialTheme.colorScheme.primaryFixed,
        disabledContainerColor = MaterialTheme.colorScheme.primaryFixed
    )

val TextButtonColorsOnBackground: ButtonColors
    @Composable get() = ButtonDefaults.textButtonColors(
        contentColor = MaterialTheme.colorScheme.onBackground
    )