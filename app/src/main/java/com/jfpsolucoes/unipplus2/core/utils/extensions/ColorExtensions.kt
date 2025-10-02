package com.jfpsolucoes.unipplus2.core.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.jfpsolucoes.unipplus2.R

val Color.primaryBackgroundLow: Color
    @Composable
    get() = colorResource(R.color.primaryBackgroundLow)