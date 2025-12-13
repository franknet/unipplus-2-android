package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundLow
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer

@Composable
fun UPHomeNavigationBar(
    onClickMenu: () -> Unit = {},
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryBackgroundLow,
        contentColor = Color.White
    ) {
        Spacer(Modifier.weight(1f))

        IconButton(onClickMenu) {
            Icon(
                painter = painterResource(R.drawable.ic_outline_menu_24),
                contentDescription = null
            )
        }
        HorizontalSpacer()
    }
}

@Preview
@Composable
private fun UPHomeNavigationBarPreview() {
    UPHomeNavigationBar()
}