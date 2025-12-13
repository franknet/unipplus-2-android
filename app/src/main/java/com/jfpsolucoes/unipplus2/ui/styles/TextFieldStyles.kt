package com.jfpsolucoes.unipplus2.ui.styles

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val defaultTextFieldColors: TextFieldColors
    @Composable get() = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,

        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,

        focusedIndicatorColor = Color.White,
        unfocusedIndicatorColor = Color.White,

        focusedPlaceholderColor = Color.White.copy(0.5f),
        unfocusedPlaceholderColor = Color.White.copy(0.5f),

        focusedTrailingIconColor = Color.White,
        unfocusedTrailingIconColor = Color.White,

        cursorColor = Color.White,
    )