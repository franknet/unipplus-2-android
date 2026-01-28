package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPFinancialSegmentButton(
    modifier: Modifier = Modifier,
    options: List<String> = emptyList(),
    selectedIndex: Int = 0,
    onSelected: (Int) -> Unit = {}
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onSelected(index) },
                selected = index == selectedIndex
            ) {
                Text(label)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UPFinancialSegmentedButtonPreview() {
    UNIPPlus2Theme {
        var selectedIndex by remember { mutableIntStateOf(1) }

        Column {
            UPFinancialSegmentButton(
                modifier = Modifier.fillMaxWidth(),
                options = listOf("teste", "teste"),
                selectedIndex = selectedIndex,
                onSelected = { selectedIndex = it }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            )
        }
    }
}