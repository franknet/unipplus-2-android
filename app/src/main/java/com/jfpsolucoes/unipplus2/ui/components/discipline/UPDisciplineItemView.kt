package com.jfpsolucoes.unipplus2.ui.components.discipline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.ui.components.progressindicator.UPVerticalLinearProgressIndicatorView
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme
import kotlin.random.Random

private typealias LazyListScopeLambda = LazyListScope.() -> Unit

interface UPDisciplineItemScope {
    fun item(content: @Composable () -> Unit)

    fun item(
        modifier: Modifier = Modifier,
        label: String = "",
        description: String = "",
        fraction: Float = 0f
    )
}

class UPDisciplineItemScopeImpl : UPDisciplineItemScope {
    data class Item(
        val modifier: Modifier,
        val label: String,
        val description: String,
        val fraction: Float
    )

    val items = mutableListOf<Item>()

    var itemContent: (@Composable () -> Unit)? = null

    override fun item(content: @Composable () -> Unit) {
        itemContent = content
    }

    override fun item(modifier: Modifier, label: String, description: String, fraction: Float) {
        items.add(Item(modifier, label, description, fraction))
    }
}

@Composable
private fun rememberScope(
    content: UPDisciplineItemScope.() -> Unit
): State<UPDisciplineItemScopeImpl> {
    val lastScope = rememberUpdatedState(content)
    return remember {
        derivedStateOf { UPDisciplineItemScopeImpl().apply(lastScope.value) }
    }
}

@Composable
private fun infoItemFor(scope: UPDisciplineItemScopeImpl): LazyListScopeLambda = {
    items(scope.items) {
        UPDisciplineItemInfoView(
            modifier = it.modifier,
            label = it.label,
            description = it.description,
            fraction = it.fraction
        )
    }
}

@Composable
private fun UPDisciplineItemHeaderView(
    modifier: Modifier = Modifier,
    title: String = "",
    trailingContent: (@Composable () -> Unit)? = null,
) = Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        modifier = modifier.weight(1f),
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
    trailingContent?.invoke()
}

@Composable
private fun UPDisciplineItemInfoView(
    modifier: Modifier = Modifier,
    label: String,
    description: String,
    fraction: Float
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        UPVerticalLinearProgressIndicatorView(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(width = 6.dp, height = 48.dp),
            indicatorText = description,
            percentage = fraction
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun UPDisciplineItemView(
    modifier: Modifier = Modifier,
    title: String = "",
    message: String? = null,
    content: UPDisciplineItemScope.() -> Unit
) = Card(
    modifier = modifier
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UPDisciplineItemHeaderView(title = title)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        val scope by rememberScope(content)

        scope.itemContent?.invoke()

        if (scope.items.isNotEmpty())
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = infoItemFor(scope)
            )

        if (message != null)
            Text(
                text = message,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
    }
}

@Preview(showBackground = true)
@Composable
private fun UPDisciplineItemViewPreview() {
    UNIPPlus2Theme {
        UPDisciplineItemView(
            title = "Disciplina",
            message = "Clique para ver mais"
        ) {
            (1..5).forEach {
                item(
                    label = "NP1",
                    description = "10",
                    fraction = Random.nextFloat()
                )
            }
        }
    }
}