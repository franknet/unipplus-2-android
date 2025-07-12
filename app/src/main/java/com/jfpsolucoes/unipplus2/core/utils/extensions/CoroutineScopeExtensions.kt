package com.jfpsolucoes.unipplus2.core.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.perform(block: suspend () -> Unit) {
    launch { block.invoke() }
}