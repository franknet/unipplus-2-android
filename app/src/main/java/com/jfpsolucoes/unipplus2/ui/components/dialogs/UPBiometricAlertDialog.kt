package com.jfpsolucoes.unipplus2.ui.components.dialogs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer

@Composable
fun UPBiometricAlertDialog(
    isAvailable: Boolean = UPBiometricManagerImpl.isBiometricAvailable,
    onClickOk: () -> Unit,
    onClickCancel: () -> Unit
) {
    if (isAvailable) {
        UPAlertDialog(
            title = stringResource(R.string.biometric_title_text),
            message = stringResource(R.string.biometric_enable_warning_text),
        ) {
            TextButton(onClick = onClickOk) {
                Text("OK")
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = onClickCancel) {
                Text(stringResource(R.string.common_cancel_text))
            }
        }
    }

}