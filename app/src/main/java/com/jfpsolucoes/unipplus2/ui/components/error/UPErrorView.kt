package com.jfpsolucoes.unipplus2.ui.components.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@Composable
fun UPErrorView(
    modifier: Modifier = Modifier,
    error: Throwable? = null,
    onClickTryAgain: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(error?.message ?: "Erro desconhecido")

        onClickTryAgain?.let {
            VerticalSpacer()

            TextButton(
                onClick = it
            ) {
                Text("Tentar novamente")
            }
        }
    }
}