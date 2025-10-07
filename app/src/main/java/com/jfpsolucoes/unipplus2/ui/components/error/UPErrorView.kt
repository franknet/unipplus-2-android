package com.jfpsolucoes.unipplus2.ui.components.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
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
        Text(
            error?.message ?: "Erro desconhecido",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        onClickTryAgain?.let {
            VerticalSpacer()

            Button(
                onClick = it
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_outline_refresh_24),
                    contentDescription = null
                )

                Spacer(Modifier.width(4.dp))

                Text("Tentar novamente")
            }
        }
    }
}

@Preview
@Composable
private fun UPErrorViewPreview() {
    UPErrorView {}
}