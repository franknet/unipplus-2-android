package com.jfpsolucoes.unipplus2.ui.components.discipline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UPDisciplineSectionView(
    modifier: Modifier = Modifier,
    title: String = "",
    trailingContent: (@Composable () -> Unit)? = null
) = Row(
    modifier = modifier
        .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Text(modifier = Modifier.weight(1f), text = title)
    trailingContent?.invoke()
}