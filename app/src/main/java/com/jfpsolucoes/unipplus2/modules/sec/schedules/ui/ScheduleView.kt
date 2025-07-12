package com.jfpsolucoes.unipplus2.modules.sec.schedules.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.ui.components.HorizontalCalendar

@Composable
fun ScheduleView(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
) {
    HorizontalCalendar(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        text = "Agenda",
        style = MaterialTheme.typography.titleLarge
    )

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(24) {
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "$it:00")

                Card {
                    Text(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(), text = "Event name")
                }
            }
        }
    }

}