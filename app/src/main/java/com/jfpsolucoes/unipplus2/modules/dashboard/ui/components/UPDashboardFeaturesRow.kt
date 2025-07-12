@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.jfpsolucoes.unipplus2.modules.dashboard.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.modules.dashboard.domain.models.UPDashBoardNavigationItem
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.theme.primaryBackgroundHigh

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun UPDashboardFeaturesRow(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    onClick: (ThreePaneScaffoldDestinationItem<UPDashBoardNavigationItem>) -> Unit
) = Row(
    modifier = modifier.horizontalScroll(scrollState),
    horizontalArrangement = Arrangement.spacedBy(16.dp)
) {
    VerticalSpacer()
    UPDashBoardNavigationItem.entries.forEach {
        Item(
            iconImage = it.icon,
            labelText = it.label,
            enabled = it.enabled,
            onClick = {
                onClick.invoke(it.destination)
            }
        )
    }
    VerticalSpacer()
}

@Composable
private fun Item(
    iconImage: ImageVector,
    labelText: String,
    selected: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) = Card(
    onClick = onClick,
    enabled = enabled,
    colors = containerColorFor(selected)
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(imageVector = iconImage, contentDescription = "")
        Text(text = labelText)
    }
}

@Composable
private fun containerColorFor(selected: Boolean): CardColors {
    if (!selected) return CardDefaults.cardColors()
    return CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryBackgroundHigh,
        contentColor = Color.White
    )
}

@Preview
@Composable
private fun UPDashboardFeaturesRowPreview() {
    UPDashboardFeaturesRow {}
}