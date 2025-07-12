package com.jfpsolucoes.unipplus2.core.utils.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

@OptIn(ExperimentalMaterial3Api::class)
val TimePickerState.timeString: String
    get() = "${
        this.hour.toString().padStart(2, '0')
    }:${
        this.minute.toString().padStart(2, '0')
    }"

