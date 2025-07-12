package com.jfpsolucoes.unipplus2.ui.components.features

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun UPFeatureCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconDescription: String = "",
    label: String,
    onClick: () -> Unit = {},
) = Card(
    onClick = onClick
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = iconDescription)
        Text(text = label)
    }
}