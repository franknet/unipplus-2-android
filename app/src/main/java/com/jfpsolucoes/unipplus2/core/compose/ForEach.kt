package com.jfpsolucoes.unipplus2.core.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <E> ForEach(items: List<E>?, action: @Composable (E) -> Unit) {
    items?.forEach { action(it) }
}

@Composable
fun <E> ForEachIndexed(items: List<E>?, action: @Composable (E, Int) -> Unit) {
    items?.forEachIndexed { index, item -> action(item, index) }
}

@Composable
fun <E> ForEachColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    showDivider: Boolean = false,
    items: List<E>?,
    content: @Composable ColumnScope.(E) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        items?.forEach { item ->
            content(this, item)
            if (showDivider && items.last() != item) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun <E> LazyForEachColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    showDivider: Boolean = false,
    items: List<E>?,
    header: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    content: LazyListScope.(E) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        item { header.invoke() }
        items?.forEach { item ->
            content.invoke(this, item)
            if (showDivider && items.last() != item) {
                item {
                    HorizontalDivider()
                }
            }
        }
        item { footer.invoke() }
    }
}