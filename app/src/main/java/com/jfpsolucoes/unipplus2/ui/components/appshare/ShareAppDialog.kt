package com.jfpsolucoes.unipplus2.ui.components.appshare

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.database.SHARED_KEY_APP_SHARE_ALERT_COUNT
import com.jfpsolucoes.unipplus2.core.database.SharedPreferencesManager
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigKeys
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.ui.components.dialogs.UPAlertDialog

@Composable
fun ShareAppDialog(modifier: Modifier = Modifier) {
    val enabled = RemoteConfigManager.getBoolean(RemoteConfigKeys.APP_SHARE_ENABLED)
    if (!enabled) return

    val rateAppCount = SharedPreferencesManager.getInt(SHARED_KEY_APP_SHARE_ALERT_COUNT)
    var showDialog by true.saveableMutableState

    // The rate will be added every 10 times the user login
    if (rateAppCount < 10) return

    val activity = activity
    val shareContent = RemoteConfigManager.getString(RemoteConfigKeys.APP_SHARE_TEXT)

    if (showDialog) {
        val shareButtonText = stringResource(R.string.common_share_text)

        UPAlertDialog(message = stringResource(R.string.common_share_description_text), onDismiss = {
            SharedPreferencesManager.setInt(SHARED_KEY_APP_SHARE_ALERT_COUNT, 0)
            showDialog = false
        }) {
            Button(onClick = {


                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent)

                val chooserIntent = Intent.createChooser(shareIntent, shareButtonText)
                activity.startActivity(chooserIntent)

                showDialog = false
            }) {
                Text(shareButtonText)
            }
        }
    }
}

@Preview
@Composable
private fun ShareAppDialogPreview() {
    ShareAppDialog()
}