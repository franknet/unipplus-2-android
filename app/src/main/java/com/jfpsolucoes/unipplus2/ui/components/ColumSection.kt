package com.jfpsolucoes.unipplus2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

enum class SectionArrangementType {
    VERTICAL, HORIZONTAL
}

@Composable
fun Section(
    modifier: Modifier = Modifier,
    arrangementType: SectionArrangementType,
    title: (RowScopeLambda)? = null,
    items: ComposeLambda,
) = Column(
    modifier = modifier
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        title?.invoke(this)
    }

    Card {
        when (arrangementType) {
            SectionArrangementType.VERTICAL -> Column {
                items.invoke()
            }
            SectionArrangementType.HORIZONTAL -> Row {
                items.invoke()
            }
        }
    }
}