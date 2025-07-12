package com.jfpsolucoes.unipplus2.modules.sec.student_records.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jfpsolucoes.unipplus2.core.utils.extensions.timeString
import com.jfpsolucoes.unipplus2.ui.theme.Typography
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRecordsTimeTextField(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(4.dp)
) {
    val calendar = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialMinute = calendar.get(Calendar.MINUTE),
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        is24Hour = true
    )

    var showTimePickerDialog by remember {
        mutableStateOf(false)
    }

    if (showTimePickerDialog) Dialog(onDismissRequest = {
        showTimePickerDialog = false
    }) {
        TimePicker(state = timePickerState)
    }

    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        text = "Horário"
    )

    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = timePickerState.timeString,
            style = Typography.bodyLarge
        )

        IconButton(onClick = { showTimePickerDialog = true }) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = ""
            )
        }
    }
}