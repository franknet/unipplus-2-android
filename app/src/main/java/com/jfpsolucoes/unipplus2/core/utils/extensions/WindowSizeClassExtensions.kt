package com.jfpsolucoes.unipplus2.core.utils.extensions

import androidx.window.core.layout.WindowSizeClass

val WindowSizeClass.isWidthMediumLowerBound: Boolean
    get() = isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

val WindowSizeClass.isWidthLargeLowerBound: Boolean
    get() = isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_LARGE_LOWER_BOUND)

val WindowSizeClass.isWidthExtraLargeLowerBound: Boolean
    get() = isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXTRA_LARGE_LOWER_BOUND)

val WindowSizeClass.isWidthExpandedLowerBound: Boolean
    get() = isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)