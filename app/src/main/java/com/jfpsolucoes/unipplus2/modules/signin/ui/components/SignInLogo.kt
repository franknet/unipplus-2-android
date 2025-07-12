package com.jfpsolucoes.unipplus2.modules.signin.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R

@Composable
fun SignInLogo(modifier: Modifier = Modifier) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    val icLogo = painterResource(id = R.drawable.ic_launcher_foreground)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            modifier = Modifier.size(80.dp),
            painter = icLogo,
            contentDescription = ""
        )

        Text(
            text = "Feito por aluno para alunos",
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInLogoPreview() {
    SignInLogo(modifier = Modifier.fillMaxWidth())
}