package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.ui.UPIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPStudentRecordsTopBar(
    title: String?,
    navigationButtonEnabled: Boolean = true,
    webViewButtonEnabled: Boolean = true,
    onClickBack: () -> Unit = {},
    onClickOpenUrl: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(title.value) },
        navigationIcon = {
            if (navigationButtonEnabled) {
                IconButton(onClick = onClickBack) {
                    Icon(painter = painterResource(R.drawable.ic_outline_arrow_back_24), contentDescription = "")
                }
            }
        },
        actions = {
            if (webViewButtonEnabled) {
                IconButton(onClick = onClickOpenUrl) {
                    Icon(painter = UPIcons.Outlined.of("ic_globe"), contentDescription = "")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        )
    )
}