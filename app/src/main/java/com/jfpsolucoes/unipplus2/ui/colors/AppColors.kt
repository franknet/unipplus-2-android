package com.jfpsolucoes.unipplus2.ui.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.jfpsolucoes.unipplus2.R

val ColorScheme.primaryBackgroundLow: Color
    @Composable get() = colorResource(id = R.color.primaryBackgroundLow)

val ColorScheme.primaryBackgroundHigh: Color
    @Composable get() = colorResource(id = R.color.primaryBackgroundHigh)

val ColorScheme.primaryBackgroundHighContrast: Color
    @Composable get() = colorResource(id = R.color.primaryBackground_highContrast)

val AppColorScheme: ColorScheme
    @Composable get() = ColorScheme(
        primary = colorResource(R.color.primary),
        onPrimary = colorResource(R.color.onPrimary),
        primaryContainer = colorResource(R.color.primaryContainer),
        onPrimaryContainer = colorResource(R.color.onPrimaryContainer),
        inversePrimary = colorResource(R.color.inversePrimary),
        secondary = colorResource(R.color.secondary),
        onSecondary = colorResource(R.color.onSecondary),
        secondaryContainer = colorResource(R.color.secondaryContainer),
        onSecondaryContainer = colorResource(R.color.onSecondaryContainer),
        tertiary = colorResource(R.color.tertiary),
        onTertiary = colorResource(R.color.onTertiary),
        tertiaryContainer = colorResource(R.color.tertiaryContainer),
        onTertiaryContainer = colorResource(R.color.onTertiaryContainer),
        background = colorResource(R.color.background),
        onBackground = colorResource(R.color.onBackground),
        surface = colorResource(R.color.surface),
        onSurface = colorResource(R.color.onSurface),
        surfaceVariant = colorResource(R.color.surfaceVariant),
        onSurfaceVariant = colorResource(R.color.onSurfaceVariant),
        surfaceTint = colorResource(R.color.surfaceTint),
        inverseSurface = colorResource(R.color.inverseSurface),
        inverseOnSurface = colorResource(R.color.inverseOnSurface),
        error = colorResource(R.color.error),
        onError = colorResource(R.color.onError),
        errorContainer = colorResource(R.color.errorContainer),
        onErrorContainer = colorResource(R.color.onErrorContainer),
        outline = colorResource(R.color.outline),
        outlineVariant = colorResource(R.color.outlineVariant),
        scrim = colorResource(R.color.scrim),
        surfaceBright = colorResource(R.color.surfaceBright),
        surfaceDim = colorResource(R.color.surfaceDim),
        surfaceContainer = colorResource(R.color.surfaceContainer),
        surfaceContainerHigh = colorResource(R.color.surfaceContainerHigh),
        surfaceContainerHighest = colorResource(R.color.surfaceContainerHighest),
        surfaceContainerLow = colorResource(R.color.surfaceContainerLow),
        surfaceContainerLowest = colorResource(R.color.surfaceContainerLowest),
        primaryFixed = colorResource(R.color.primaryFixed),
        primaryFixedDim = colorResource(R.color.primaryFixedDim),
        onPrimaryFixed = colorResource(R.color.onPrimaryFixed),
        onPrimaryFixedVariant = colorResource(R.color.onPrimaryFixedVariant),
        secondaryFixed = colorResource(R.color.secondaryFixed),
        secondaryFixedDim = colorResource(R.color.secondaryFixedDim),
        onSecondaryFixed = colorResource(R.color.onSecondaryFixed),
        onSecondaryFixedVariant = colorResource(R.color.onSecondaryFixedVariant),
        tertiaryFixed = colorResource(R.color.tertiaryFixed),
        tertiaryFixedDim = colorResource(R.color.tertiaryFixedDim),
        onTertiaryFixed = colorResource(R.color.onTertiaryFixed),
        onTertiaryFixedVariant = colorResource(R.color.onTertiaryFixedVariant)
    )