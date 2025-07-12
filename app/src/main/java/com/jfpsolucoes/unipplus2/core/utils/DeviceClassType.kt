package com.jfpsolucoes.unipplus2.core.utils

import android.content.res.Configuration
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.layout.FoldingFeature
import com.jfpsolucoes.unipplus2.ui.LocalFoldingFeatures

enum class DeviceClassType {
    PHONE_COMPACT,
    PHONE_PORTRAIT,
    PHONE_LANDSCAPE,
    FLIP_COMPACT,
    FLIP_OPEN_PORTRAIT,
    FLIP_HALF_OPEN_PORTRAIT,
    FLIP_OPEN_LANDSCAPE,
    FLIP_HALF_OPEN_LANDSCAPE,
    FOLDABLE_OPEN_PORTRAIT,
    FOLDABLE_HALF_OPEN_PORTRAIT,
    FOLDABLE_OPEN_LANDSCAPE,
    FOLDABLE_HALF_OPEN_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    NONE;

    val isPhone: Boolean
        get() = listOf(
            PHONE_COMPACT,
            PHONE_PORTRAIT,
            PHONE_LANDSCAPE,
            FLIP_COMPACT,
            FLIP_OPEN_PORTRAIT,
            FLIP_HALF_OPEN_PORTRAIT,
            FLIP_OPEN_LANDSCAPE,
            FLIP_HALF_OPEN_LANDSCAPE
        ).contains(this)

    val isTablet: Boolean
        get() = listOf(FOLDABLE_OPEN_PORTRAIT,
            FOLDABLE_HALF_OPEN_PORTRAIT,
            FOLDABLE_OPEN_LANDSCAPE,
            FOLDABLE_HALF_OPEN_LANDSCAPE,
            TABLET_PORTRAIT,
            TABLET_LANDSCAPE
        ).contains(this)

    val isFoldable: Boolean
        get() = listOf(
            FOLDABLE_OPEN_PORTRAIT,
            FOLDABLE_HALF_OPEN_PORTRAIT,
            FOLDABLE_OPEN_LANDSCAPE,
            FOLDABLE_HALF_OPEN_LANDSCAPE
        ).contains(this)
}

val WindowHeightSizeClass.isCompact: Boolean
    get() = this == WindowHeightSizeClass.COMPACT

val WindowHeightSizeClass.isMedium: Boolean
    get() = this == WindowHeightSizeClass.MEDIUM

val WindowHeightSizeClass.isExpanded: Boolean
    get() = this == WindowHeightSizeClass.EXPANDED

val WindowWidthSizeClass.isCompact: Boolean
    get() = this == WindowWidthSizeClass.COMPACT

val WindowWidthSizeClass.isMedium: Boolean
    get() = this == WindowWidthSizeClass.MEDIUM

val WindowWidthSizeClass.isExpanded: Boolean
    get() = this == WindowWidthSizeClass.EXPANDED

val FoldingFeature.State.isFlat: Boolean
    get() = this == FoldingFeature.State.FLAT

val WindowSizeClass.isPhone: Boolean
    get() = windowWidthSizeClass.isCompact || windowHeightSizeClass.isCompact


val WindowAdaptiveInfo.deviceClass: DeviceClassType
    @Composable get() {
    val foldingFeature = LocalFoldingFeatures.current?.last()
    val orientation = LocalConfiguration.current.orientation
    val orientationPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
    val orientationLandscape = !orientationPortrait
    val widthSize = windowSizeClass.windowWidthSizeClass
    val heightSize = windowSizeClass.windowHeightSizeClass

    return if (foldingFeature != null) when {
        widthSize.isCompact && heightSize.isCompact -> DeviceClassType.FLIP_COMPACT

        windowSizeClass.isPhone && orientationPortrait -> {
            if (foldingFeature.state.isFlat) DeviceClassType.FLIP_OPEN_PORTRAIT
            else DeviceClassType.FLIP_HALF_OPEN_PORTRAIT
        }
        windowSizeClass.isPhone && orientationLandscape -> {
            if (foldingFeature.state.isFlat) DeviceClassType.FLIP_OPEN_LANDSCAPE
            else DeviceClassType.FLIP_HALF_OPEN_LANDSCAPE
        }
        !windowSizeClass.isPhone && orientationPortrait  -> {
            if (foldingFeature.state.isFlat) DeviceClassType.FOLDABLE_OPEN_PORTRAIT
            else DeviceClassType.FOLDABLE_HALF_OPEN_PORTRAIT
        }
        !windowSizeClass.isPhone && orientationLandscape -> {
            if (foldingFeature.state.isFlat) DeviceClassType.FOLDABLE_OPEN_LANDSCAPE
            else DeviceClassType.FOLDABLE_HALF_OPEN_LANDSCAPE
        }
        else -> DeviceClassType.NONE
    } else when {
        widthSize.isCompact && heightSize.isCompact -> DeviceClassType.PHONE_COMPACT

        windowSizeClass.isPhone && orientationPortrait -> DeviceClassType.PHONE_PORTRAIT
        windowSizeClass.isPhone && orientationLandscape -> DeviceClassType.PHONE_LANDSCAPE

        !windowSizeClass.isPhone && orientationPortrait -> DeviceClassType.TABLET_PORTRAIT
        !windowSizeClass.isPhone && orientationLandscape -> DeviceClassType.TABLET_LANDSCAPE

        else -> DeviceClassType.NONE
    }
}