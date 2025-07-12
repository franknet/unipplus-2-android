package com.jfpsolucoes.unipplus2.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.jfpsolucoes.unipplus2.R

val ColorScheme.primaryBackgroundLow: Color
    @Composable get() = colorResource(id = R.color.primaryBackgroundLow)

val ColorScheme.primaryBackgroundHigh: Color
    @Composable get() = colorResource(id = R.color.primaryBackgroundHigh)

@Composable
private fun appColors() = ColorScheme(
    primary = colorResource(id = R.color.primary),
    onPrimary = colorResource(id = R.color.onPrimary),
    primaryContainer = colorResource(id = R.color.primaryContainer),
    onPrimaryContainer = colorResource(id = R.color.onPrimaryContainer),
    inversePrimary = colorResource(id = R.color.inversePrimary),
    secondary = colorResource(id = R.color.secondary),
    onSecondary = colorResource(id = R.color.onSecondary),
    secondaryContainer = colorResource(id = R.color.secondaryContainer),
    onSecondaryContainer = colorResource(id = R.color.onSecondaryContainer),
    tertiary = colorResource(id = R.color.tertiary),
    onTertiary = colorResource(id = R.color.onTertiary),
    tertiaryContainer = colorResource(id = R.color.tertiaryContainer),
    onTertiaryContainer = colorResource(id = R.color.onTertiaryContainer),
    background = colorResource(id = R.color.background),
    onBackground = colorResource(id = R.color.onBackground),
    surface = colorResource(id = R.color.surface),
    onSurface = colorResource(id = R.color.onSurface),
    surfaceVariant = colorResource(id = R.color.surfaceVariant),
    onSurfaceVariant = colorResource(id = R.color.onSurfaceVariant),
    surfaceTint = colorResource(id = R.color.onSurfaceVariant),
    inverseSurface = colorResource(id = R.color.inverseSurface),
    inverseOnSurface = colorResource(id = R.color.inverseOnSurface),
    error = colorResource(id = R.color.error),
    onError = colorResource(id = R.color.onError),
    errorContainer = colorResource(id = R.color.errorContainer),
    onErrorContainer = colorResource(id = R.color.onErrorContainer),
    outline = colorResource(id = R.color.outline),
    outlineVariant = colorResource(id = R.color.outlineVariant),
    scrim = colorResource(id = R.color.scrim),
    surfaceBright = colorResource(id = R.color.surfaceBright),
    surfaceDim = colorResource(id = R.color.surfaceDim),
    surfaceContainer = colorResource(id = R.color.surfaceContainer),
    surfaceContainerHigh = colorResource(id = R.color.surfaceContainerHigh),
    surfaceContainerHighest = colorResource(id = R.color.surfaceContainerHighest),
    surfaceContainerLow = colorResource(id = R.color.surfaceContainerLow),
    surfaceContainerLowest = colorResource(id = R.color.surfaceContainerLowest),
)

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun dynamicColors(darkTheme: Boolean): ColorScheme {
    val context = LocalContext.current
    return if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
}

@Composable
fun UNIPPlus2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> dynamicColors(darkTheme)
        else -> appColors()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}