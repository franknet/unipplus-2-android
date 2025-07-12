package com.jfpsolucoes.unipplus2.modules.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UPDashboardClassItem(
    modifier: Modifier = Modifier,
    disciplineName: String,
    teacherName: String
) = Row(
    modifier = modifier.padding(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Text(text = "0:00")

    Column(modifier = Modifier.weight(1f)) {
        Text(text = disciplineName)
        Text(text = teacherName, style = MaterialTheme.typography.labelMedium)
    }
}