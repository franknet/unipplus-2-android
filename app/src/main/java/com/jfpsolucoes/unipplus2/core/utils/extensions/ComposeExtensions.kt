package com.jfpsolucoes.unipplus2.core.utils.extensions

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

val activity: ComponentActivity
 @Composable
 get() = LocalContext.current as ComponentActivity

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ShowSnackbar(state: SnackbarHostState, visuals: SnackbarVisuals) {
    rememberCoroutineScope().launch {
        state.showSnackbar(visuals)
    }
}