package com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.utils.compose.RememberLaunchedEffect
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.models.UPCalendarData
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.models.UPCalendarDay
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPCalendarView(
    modifier: Modifier = Modifier,
    viewModel: UPCalendarViewModel = viewModel()
) {
    val calendarUIState by viewModel.calendarUIState.collectAsStateWithLifecycle()

    RememberLaunchedEffect {
        viewModel.trackScreenView()
        viewModel.fetch()
    }

    UPUIStateScaffold(
        modifier = modifier,
        state = calendarUIState,
        loadingContent = { UPLoadingView() },
        errorContent = { _, error ->
            UPErrorView(error = error) {
                viewModel.fetch()
            }
        },
        content = { _, data ->
            UPCalendarContent(
                data = data,
                onMonthClick = { viewModel.updateCalendar(it) }
            )
        }
    )
}

@Composable
private fun UPCalendarContent(
    data: UPCalendarData,
    onMonthClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = data.monthYear,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        VerticalSpacer(16.dp)

        // Month Selector
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(data.availableMonths) { month ->
                Card(
                    onClick = { onMonthClick(month.value) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (month.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = month.label,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = if (month.selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        VerticalSpacer(24.dp)

        // Calendar Grid
        UPCalendarGrid(data = data)
        
        VerticalSpacer(16.dp)
    }
}

@Composable
private fun UPCalendarGrid(data: UPCalendarData) {
    val weekDays = data.weekDays
    if (weekDays.isEmpty()) return

    val maxWeeks = weekDays.maxOfOrNull { it.days.size } ?: 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        // Weekday Labels
        Row(modifier = Modifier.fillMaxWidth()) {
            weekDays.forEach { weekDay ->
                Text(
                    text = weekDay.label,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        VerticalSpacer(8.dp)

        // Days
        for (weekIndex in 0 until maxWeeks) {
            Row(modifier = Modifier.fillMaxWidth()) {
                weekDays.forEach { weekDay ->
                    val day = weekDay.days.getOrNull(weekIndex)
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        if (day != null) {
                            UPCalendarDayCell(day = day)
                        }
                    }
                }
            }
            VerticalSpacer(4.dp)
        }
    }
}

@Composable
private fun UPCalendarDayCell(day: UPCalendarDay) {
    val backgroundColor = if (day.backgroundColor != 0L) Color(day.backgroundColor) else Color.Transparent
    val isToday = day.isToday

    Box(
        modifier = Modifier
            .size(44.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .then(
                if (isToday) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = day.day.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                color = if (day.backgroundColor != 0L) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
            )
            
            if (day.events.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(
                            if (day.backgroundColor != 0L) Color.White else MaterialTheme.colorScheme.primary,
                            CircleShape
                        )
                )
            }
        }
    }
}
