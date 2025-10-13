package com.jfpsolucoes.unipplus2.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPAlertDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String,
    onDismiss: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
    ) {
        Card(modifier) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(message, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions(this)
            }
        }
    }
}

@Preview
@Composable
private fun UPAlertDialogPreview() {
    UPAlertDialog(message = "Teste") {
        TextButton(onClick = { } ) {
            Text("OK")
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = { } ) {
            Text(stringResource(R.string.common_cancel_text))
        }
    }
}