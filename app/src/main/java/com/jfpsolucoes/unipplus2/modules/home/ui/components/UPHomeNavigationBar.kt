package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
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
import com.jfpsolucoes.unipplus2.core.utils.extensions.rememberState
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.ICON_HEADSET
import com.jfpsolucoes.unipplus2.ui.UPIcons
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer

@Composable
fun UPHomeNavigationBar(
    data: UPHomeSystemsResponse? = null,
    onSelectFeature: (UPSystem) -> Unit = {},
    onClickSystems: (List<UPSystem>?) -> Unit = {},
    onClickSettings: () -> Unit = {},
) {
    var indexSelected by 0.saveableMutableState

    NavigationBar {
        ForEachIndexed(data?.feature) { feature, index ->
            NavigationBarItem(
                selected = indexSelected == 0,
                onClick = { onSelectFeature(feature) },
                icon = {
                    Icon(
                        painter = UPIcons.Outlined.of(ICON_HEADSET),
                        contentDescription = null
                    )
                },
                label = {
                    Text(feature.description.value)
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (!data?.web.isNullOrEmpty()) {
            NavigationBarItem(
                selected = indexSelected == 1,
                onClick = { onClickSystems(data.web) },
                icon = {
                    Icon(
                        painter = UPIcons.Outlined.of("ic_globe"),
                        contentDescription = null
                    )
                },
                label = { Text("Sistemas") }
            )
        }

        NavigationBarItem(
            selected = indexSelected == 2,
            onClick = onClickSettings,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null
                )
            },
            label = { Text("Config.") }
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