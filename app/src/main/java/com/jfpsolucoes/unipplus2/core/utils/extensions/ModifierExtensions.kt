package com.jfpsolucoes.unipplus2.core.utils.extensions

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.shimmer(isEnabled: Boolean = false, shape: Shape = RectangleShape): Modifier = composed {
    if (!isEnabled) return@composed this

    var size by IntSize.Zero.rememberState
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2*1000)
        ),
        label = ""
    )
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFB5B5B5),
            Color(0xFFEBEBEB),
            Color(0xFFB5B5B5)
        ),
        start = Offset(startOffsetX, 0f),
        end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
    )
    background(brush = gradientBrush, shape = shape).onGloballyPositioned {
        size = it.size
    }
}
