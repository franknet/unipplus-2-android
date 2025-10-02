package com.jfpsolucoes.unipplus2.ui.components.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun UPFeatureCard(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    label: String,
    enabled: Boolean,
    message: String?,
    onClick: () -> Unit = {},
) = Card(
    enabled = enabled,
    onClick = onClick
) {
    Box(modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            icon()

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = label
            )
        }

        message?.let {
            Column(
                modifier = Modifier.matchParentSize(),
                horizontalAlignment = Alignment.End
            ) {
                Surface(
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}