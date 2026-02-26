package com.jfpsolucoes.unipplus2.ui.components.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.ui.styles.defaultCardColors

@Composable
fun UPFeatureCard(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    label: String,
    enabled: Boolean,
    message: String?,
    colors: CardColors = defaultCardColors,
    onClick: () -> Unit = {},
) = Card(
    enabled = enabled,
    onClick = onClick,
    colors = colors
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
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerHighest
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