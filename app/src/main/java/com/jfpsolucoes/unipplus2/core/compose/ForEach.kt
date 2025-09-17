package com.jfpsolucoes.unipplus2.core.compose

import androidx.compose.runtime.Composable

@Composable
fun <E> ForEach(items: List<E>?, action: @Composable (E) -> Unit) {
    items?.forEach { action(it) }
}

@Composable
fun <E> ForEachIndexed(items: List<E>?, action: @Composable (E, Int) -> Unit) {
    items?.forEachIndexed { index, item -> action(item, index) }
}