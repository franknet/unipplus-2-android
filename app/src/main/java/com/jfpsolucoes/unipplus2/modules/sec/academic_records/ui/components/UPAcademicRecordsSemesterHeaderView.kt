package com.jfpsolucoes.unipplus2.modules.sec.academic_records.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UPAcademicRecordsSemesterHeaderView(
    modifier: Modifier = Modifier,
    title: String
) = Card(
    modifier = modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
) {
    Text(
        modifier = Modifier.padding(8.dp),
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}