package com.jfpsolucoes.unipplus2.modules.sec.academic_records.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain.models.UPAcademicRecordsDiscipline
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain.models.UPAcademicRecordsDisciplineInfo
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain.models.UPAcademicRecordsDisciplineStatus
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@Composable
fun UPAcademicRecordsDisciplineView(
    modifier: Modifier = Modifier,
    discipline: UPAcademicRecordsDiscipline,
) = Column(
    modifier = modifier.padding(horizontal = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = discipline.name.value
        )
        Text(
            text = discipline.status?.description.value,
            style = MaterialTheme.typography.labelSmall
        )
    }

    if (discipline.showMessage) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = discipline.message.value,
            textAlign = TextAlign.Center
        )
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            discipline.infos?.forEach {
                UPAcademicRecordsDisciplineInfoView(
                    labelText = it.label.value,
                    valueText = it.description.value
                )
            }
        }
    }
}

@Composable
private fun UPAcademicRecordsDisciplineInfoView(
    modifier: Modifier = Modifier,
    labelText: String,
    valueText: String
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(text = labelText)
    Card(modifier = Modifier.width(50.dp)) {
        Text(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            text = valueText,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun UPAcademicRecordsSemesterViewPreview() {
    UNIPPlus2Theme {
        val info = listOf(
            UPAcademicRecordsDisciplineInfo(
                label = "Nota",
                description = "10"
            ),
            UPAcademicRecordsDisciplineInfo(
                label = "Nota",
            )
        )

        UPAcademicRecordsDisciplineView(
            discipline = UPAcademicRecordsDiscipline(
                name = "Disciplina",
                infos = info
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UPAcademicRecordsSemesterViewPreview2() {
    UNIPPlus2Theme {
        UPAcademicRecordsDisciplineView(
            discipline = UPAcademicRecordsDiscipline(
                name = "Disciplina",
                showMessage = true,
                message = "Sem informações",
                status = UPAcademicRecordsDisciplineStatus(
                    description = "Reprovado"
                )
            )
        )
    }
}