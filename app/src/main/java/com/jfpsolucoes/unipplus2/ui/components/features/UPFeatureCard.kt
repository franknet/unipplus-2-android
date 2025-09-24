package com.jfpsolucoes.unipplus2.ui.components.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
    icon: Painter,
    iconDescription: String = "",
    label: String,
    enabled: Boolean,
    message: String?,
    onClick: () -> Unit = {},
) = Card(
    enabled = enabled,
    onClick = onClick
) {
    BadgedBox(
        badge = {
            message?.let {
                Surface(modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .padding(4.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    ) {
        Column(
            modifier = modifier.padding(16.dp),
        ) {
            Icon(painter = icon, contentDescription = iconDescription)
            Text(text = label)
        }
    }
}