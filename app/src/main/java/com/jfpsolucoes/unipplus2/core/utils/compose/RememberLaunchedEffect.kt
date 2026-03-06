package com.jfpsolucoes.unipplus2.core.utils.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope

@Composable
fun RememberLaunchedEffect(
    block: suspend CoroutineScope.() -> Unit
) {
    var shouldLaunchEffect by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        if (shouldLaunchEffect) {
            block()
            shouldLaunchEffect = false
        }
    }

}