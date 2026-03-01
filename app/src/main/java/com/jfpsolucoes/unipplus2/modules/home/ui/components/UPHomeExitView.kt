package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.styles.ButtonColorsPrimaryFixed

@Composable
fun UPHomeExitView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.home_exit_question_text),
            style = MaterialTheme.typography.titleMedium
        )
        VerticalSpacer()
        Button(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = onClick,
            colors = ButtonColorsPrimaryFixed
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.common_exit_text),
                textAlign = TextAlign.Center
            )
        }
        VerticalSpacer(32.dp)
    }
}