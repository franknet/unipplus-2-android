package com.jfpsolucoes.unipplus2.modules.settings.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R

@Composable
fun UPSettingsBiometricItemView(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Card {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.biometric_enable_text))
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}