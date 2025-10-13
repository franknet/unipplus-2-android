package com.jfpsolucoes.unipplus2.modules.settings.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R

@Composable
fun UPSettingsProfileItemView(
    name: String,
    onClick: () -> Unit,
) {
    Card(onClick = onClick) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                name,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.ic_outline_account_circle_24),
                contentDescription = ""
            )
        }
    }
}