package com.jfpsolucoes.unipplus2.core.utils.extensions

import androidx.activity.compose.LocalActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

val activity: AppCompatActivity
 @Composable get() = LocalActivity.current as AppCompatActivity
