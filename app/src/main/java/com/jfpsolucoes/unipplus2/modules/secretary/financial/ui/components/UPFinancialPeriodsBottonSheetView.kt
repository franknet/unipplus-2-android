package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.core.compose.ForEach
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@Composable
fun UPFinancialPeriodsBottonSheetView(
    modifier: Modifier = Modifier,
    periods: List<String>? = null,
    selectedPeriod: String? = null,
    onSelectPeriod: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ForEach(periods) { period ->
            NavigationDrawerItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                selected = selectedPeriod == period,
                onClick = { onSelectPeriod(period) },
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = period,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            )
        }

        VerticalSpacer()
    }
}