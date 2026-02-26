package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jfpsolucoes.unipplus2.core.compose.ForEachIndexed
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialFeature
import com.jfpsolucoes.unipplus2.ui.styles.SegmentedButtonColorsPrimaryHigh

@Composable
fun UPFinancialSegmentedButton(
    modifier: Modifier = Modifier,
    features: List<UPFinancialFeature>,
    selectedIndex: Int = 0,
    colors: SegmentedButtonColors = SegmentedButtonColorsPrimaryHigh,
    onSelectFeature: (UPFinancialFeature) -> Unit = {}
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        ForEachIndexed(features) { feature, index ->
            SegmentedButton(
                enabled = feature.enabled ?: false,
                selected = selectedIndex == index,
                onClick = {
                    onSelectFeature(feature)
                },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = features.size
                ),
                colors = colors
            ) {
                Text(text = feature.title.orEmpty())
            }
        }
    }
}