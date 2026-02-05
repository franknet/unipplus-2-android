package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.jfpsolucoes.unipplus2.core.compose.ForEachColumn
import com.jfpsolucoes.unipplus2.core.compose.LazyForEachColumn
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPayment
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@Composable
fun UPFinancialExtractPaymentBottomSheetView(
    modifier: Modifier = Modifier,
    payment: UPFinancialPayment?
) {
    val isMediumWidthSize = currentWindowAdaptiveInfo().windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
    )
    val paddingValue = if (isMediumWidthSize) 100.dp else 16.dp
    LazyForEachColumn(
        modifier = modifier.padding(horizontal = paddingValue),
        items = payment?.details
    ) { detail ->
        item {
            Text(
                text = detail.label.orEmpty(),
                style = MaterialTheme.typography.titleLarge
            )
        }
        item { VerticalSpacer() }
        items(items = detail.items.orEmpty()) { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.label.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.value.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            HorizontalDivider()
            VerticalSpacer(space = 4.dp)
        }
        item { VerticalSpacer() }
    }
}