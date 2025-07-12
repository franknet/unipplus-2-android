package com.jfpsolucoes.unipplus2.ui.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    title: String,
    isLoading: Boolean = false,
    onClick: () -> Unit
) = Button(
    modifier = modifier,
    onClick = onClick,
    enabled = !isLoading,
    colors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.secondaryContainer),
        contentColor = colorResource(id = R.color.onSecondaryContainer)
    )
) {
    if (isLoading) Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp)
        )
    }
    else Text(
        modifier = Modifier.weight(1f),
        text = title,
        textAlign = TextAlign.Center
    )
}