package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer
import com.jfpsolucoes.unipplus2.ui.theme.primaryBackgroundLow

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