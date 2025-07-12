package com.jfpsolucoes.unipplus2.modules.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R

@Composable
fun UPDashboardTaskItem(
    modifier: Modifier = Modifier,
    timeText: String,
    description: String
) = Row(
    modifier = modifier.padding(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_task_list_24),
        contentDescription = ""
    )
    Column(modifier = Modifier.weight(1f)) {
        Text(text = timeText)
        Text(text = description)
    }
}