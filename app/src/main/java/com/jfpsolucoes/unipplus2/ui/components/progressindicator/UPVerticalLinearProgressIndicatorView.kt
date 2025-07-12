package com.jfpsolucoes.unipplus2.ui.components.progressindicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@Composable
fun UPVerticalLinearProgressIndicatorView(
    modifier: Modifier = Modifier,
    indicatorText: String = "",
    indicatorColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    percentage: Float = 0.5f
) {
    Column(
        modifier = modifier
        .background(color = containerColor, shape = CircleShape),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .height(1.dp)
        ) {
            Text(
                modifier = Modifier
                    .requiredWidth(50.dp)
                    .requiredHeight(40.dp),
                text = indicatorText,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = percentage)
                .fillMaxWidth()
                .background(color = indicatorColor, shape = CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UPVerticalLinearProgressIndicatorViewPreview() {
    UNIPPlus2Theme {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            UPVerticalLinearProgressIndicatorView(
                modifier = Modifier.size(6.dp, 80.dp),
                indicatorText = "10.0"
            )
        }
    }
}