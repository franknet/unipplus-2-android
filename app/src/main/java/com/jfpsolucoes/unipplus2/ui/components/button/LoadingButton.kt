package com.jfpsolucoes.unipplus2.ui.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.ui.styles.defaultButtonColors
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    title: String,
    isLoading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) = Button(
    modifier = modifier,
    onClick = onClick,
    enabled = !isLoading,
    colors = colors
) {
    Spacer(modifier = Modifier.weight(1f))

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center
            )
        )
    }

    Spacer(modifier = Modifier.weight(1f))
}

@Preview
@Composable
private fun LoadingButtonPreview() {
    UNIPPlus2Theme(darkTheme = true) {
        LoadingButton(
            title = "Entrar",
            isLoading = false
        ) {}
    }
}