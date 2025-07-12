package com.jfpsolucoes.unipplus2.modules.dashboard.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.ui.components.ComposeLambda
import com.jfpsolucoes.unipplus2.ui.components.Section
import com.jfpsolucoes.unipplus2.ui.components.SectionArrangementType
import com.jfpsolucoes.unipplus2.ui.theme.Typography
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@Composable
fun UPDashboardClassesSection(
    modifier: Modifier = Modifier,
    items: ComposeLambda
) = Section(
    modifier = modifier,
    arrangementType = SectionArrangementType.VERTICAL,
    title = {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            text = "Aulas (5)",
            style = Typography.titleLarge
        )

        TextButton(onClick = {}) {
            Text(text = "Ver mais")
        }
    },
    items = items
)

@Preview(showBackground = true)
@Composable
private fun UPDashboardClassesSectionPreview() {
    UNIPPlus2Theme {
        UPDashboardClassesSection {
            (0..2).forEach {
                UPDashboardClassItem(
                    disciplineName = "Discipline ${it+1}",
                    teacherName = "Teacher name"
                )
            }
        }
    }
}
