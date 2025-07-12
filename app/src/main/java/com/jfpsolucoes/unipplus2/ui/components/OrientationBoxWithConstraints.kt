package com.jfpsolucoes.unipplus2.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface OrientationBoxWithConstraintsScope {
    @Composable fun item(modifier: Modifier, content: @Composable () -> Unit)
}

class OrientationBoxWithConstraintsScopeInstance: OrientationBoxWithConstraintsScope {
    @Composable
    override fun item(modifier: Modifier, content: @Composable () -> Unit) {

    }

}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun OrientationBoxWithConstraints(
    modifier: Modifier = Modifier,
    content: @Composable OrientationBoxWithConstraintsScope.() -> Unit
) = BoxWithConstraints(
    modifier = modifier
) {
    if (this.maxHeight > this.maxWidth) {
        // Portrait
        Column {
//            content.invoke(this@BoxWithConstraints)
        }
    } else {
        // Landscape
        Row {
//            content.invoke(this@BoxWithConstraints)
        }
    }
}