package com.jfpsolucoes.unipplus2.modules.settings.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.styles.secondCardColors

@Composable
fun UPSettingsBiometricItemView(
    biometricChecked: Boolean,
    onBiometricCheckedChange: (Boolean) -> Unit,
    autoSignChecked: Boolean,
    onAutoSignCheckedChange: (Boolean) -> Unit,
    colors: CardColors = secondCardColors,
) {
    Card(
        colors = colors
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.biometric_enable_text))

                Switch(
                    checked = biometricChecked,
                    onCheckedChange = onBiometricCheckedChange
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.common_auto_sign_in_text))

                Switch(
                    checked = autoSignChecked,
                    onCheckedChange = onAutoSignCheckedChange
                )
            }
        }
    }
}