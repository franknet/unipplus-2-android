package com.jfpsolucoes.unipplus2.ui.components.unipplus.student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.ui.styles.defaultCardColors
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@Composable
fun UPStudentInfoCard(
    modifier: Modifier = Modifier,
    name: String,
    course: String,
    colors: CardColors = defaultCardColors
) {
    Card(
        modifier,
        colors = colors
    ) {
        Icon(
            modifier = Modifier.padding(top =16.dp, start = 16.dp).size(56.dp),
            painter = painterResource(R.drawable.ic_outline_account_circle_24),
            contentDescription = ""
        )

        Column(Modifier.padding(16.dp).fillMaxWidth()) {
            Text(name, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(course, style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview
@Composable
private fun UPStudentInfoCardPreview() {
    UNIPPlus2Theme(darkTheme = false) {
        UPStudentInfoCard(
            name = "Nome do Aluno",
            course = "Curso do Aluno"
        )
    }
}