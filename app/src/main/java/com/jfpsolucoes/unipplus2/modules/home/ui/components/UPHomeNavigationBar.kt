package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jfpsolucoes.unipplus2.core.compose.ForEachIndexed
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.ICON_HEADSET
import com.jfpsolucoes.unipplus2.ui.UPIcons

@Composable
fun UPHomeNavigationBar(
    data: UPHomeSystemsResponse? = null,
    onSelectFeature: (UPSystem) -> Unit = {},
    onClickMenu: () -> Unit = {},
) {
    NavigationBar {

        Spacer(Modifier.weight(1f))

        NavigationBarItem(
            selected = false,
            onClick = onClickMenu,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null
                )
            },
            label = { }
        )

    }
}

@Preview
@Composable
private fun UPHomeNavigationBarPreview() {
    UPHomeNavigationBar(
        data = UPHomeSystemsResponse(
            feature = listOf(
                UPSystem(id = 0)
            ),
            web = listOf(
                UPSystem(id = 0)
            )
        )
    )
}