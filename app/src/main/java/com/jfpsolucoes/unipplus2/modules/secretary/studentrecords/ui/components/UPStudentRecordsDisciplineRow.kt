package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.core.compose.ForEach
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models.UPStudentRecordsDiscipline
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.styles.secondCardColors

@Composable
fun UPStudentRecordsDisciplineRow(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    discipline: UPStudentRecordsDiscipline?,
    colors: CardColors = secondCardColors
) {
    val statusColor = if (discipline?.status?.color == 0L) colors.containerColor else Color(discipline?.status?.color ?: 0L)
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = statusColor
        )
    ) {
        Surface(
            modifier = Modifier
                .padding(start = 4.dp),
            color = colors.containerColor
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            )
            {
                Text(
                    text = discipline?.name.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                VerticalSpacer()

                HorizontalDivider()

                VerticalSpacer(space = 4.dp)

                ForEach(discipline?.items) { item ->
                    Row(
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = item.label.orEmpty(),
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Box(
                            contentAlignment = Center
                        ) {
                            val progressColor = Color(item.color ?: ProgressIndicatorDefaults.circularColor.toArgb().toLong())
                            CircularProgressIndicator(
                                progress = { item.percentage?.toFloat() ?: 0f },
                                strokeWidth = 2.dp,
                                color = progressColor,
                                trackColor = progressColor.copy(alpha = 0.25f),
                            )
                            Text(
                                text = item.score.orEmpty(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    VerticalSpacer(space = 4.dp)
                }

                discipline?.status?.let { status ->
                    HorizontalDivider()
                    VerticalSpacer()
                    Text(
                        text = status.message.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}