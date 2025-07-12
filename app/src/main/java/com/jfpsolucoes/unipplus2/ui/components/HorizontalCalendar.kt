package com.jfpsolucoes.unipplus2.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.core.utils.extensions.format
import com.jfpsolucoes.unipplus2.core.utils.extensions.resetTime
import com.jfpsolucoes.unipplus2.core.utils.extensions.setDay
import com.jfpsolucoes.unipplus2.ui.theme.Typography
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    eventsDates: List<String> = emptyList(),
    onEventClick: (Date) -> Unit = {}
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    val coroutine = rememberCoroutineScope()
    val calendar = Calendar.getInstance()
    val daysCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val today = calendar.get(Calendar.DAY_OF_MONTH)
    val headerDate = calendar.time.format("MMMM (yyyy)")
    var rootCoordinates: LayoutCoordinates? = null
    var autoScroll = true

    Text(
        modifier = Modifier.padding(start = 4.dp),
        style = Typography.titleLarge,
        text = headerDate
    )

    Row(
        modifier = Modifier
            .onGloballyPositioned { rootCoordinates = it }
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
    ) {
        (1..daysCount).forEach { day ->
            val dayOfWeek = calendar.setDay(day).time.format("EE")
            val isDaySelected = day == today
            val dayOfWeekString = calendar.resetTime().time.format("yyyy-MM-dd")

            HorizontalCalendarUnitView(
                modifier = Modifier
                    .onGloballyPositioned {
                        if (isDaySelected && autoScroll) {
                            val selectedDayCoordinate =
                                it.positionInParent().x + (it.size.width / 2)
                            val rootMiddleWith = rootCoordinates!!.size.width / 2
                            val scrollPosition = selectedDayCoordinate - rootMiddleWith
                            coroutine.launch {
                                scrollState.scrollTo(scrollPosition.toInt())
                                autoScroll = false
                            }
                        }
                    },
                onClick = { onEventClick(calendar.time) },
                isSelected = isDaySelected,
                containsEvent = eventsDates.contains(dayOfWeekString),
                dayOfWeek = dayOfWeek,
                day = "$day"
            )
        }
    }
}


@Composable
private fun HorizontalCalendarUnitView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isSelected: Boolean = true,
    containsEvent: Boolean = false,
    dayOfWeek: String,
    day: String,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Unspecified else Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = dayOfWeek)
            Text(text = day)

            if (containsEvent) {
                EventDot(modifier = Modifier)
            }
        }
    }
}

@Composable
private fun EventDot(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(4.dp)
            .background(color = MaterialTheme.colorScheme.inversePrimary, shape = CircleShape)
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
private fun HorizontalCalendarPreview() {
    val events = listOf(
        "2024-08-10",
        "2024-08-15",
        "2024-08-16",
    )
    UNIPPlus2Theme(darkTheme = true) {
        Scaffold {
            HorizontalCalendar(modifier = Modifier.fillMaxWidth(), eventsDates = events)
        }
    }
}