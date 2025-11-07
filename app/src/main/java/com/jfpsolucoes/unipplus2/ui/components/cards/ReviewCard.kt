package com.jfpsolucoes.unipplus2.ui.components.cards

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R

@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.review_text),
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            onClick = onClick
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.review_button_text),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ReviewCardPreview() {
    ReviewCard {}
}