package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.compose.ForEachIndexed
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem

@Composable
fun UPHomeNavigationRail(
    modifier: Modifier = Modifier,
    data: UPHomeSystemsResponse? = null,
    onSelectFeature: (UPSystem) -> Unit = {},
    onClickSystems: (List<UPSystem>?) -> Unit = {},
    onClickSettings: () -> Unit = {},
    onClickExit: () -> Unit = {}
) {
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        ForEachIndexed(data?.feature) { feature, index ->
            NavigationRailItem(
                selected = false,
                onClick = { onSelectFeature(feature) },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                label = { Text(feature.description.value ) }
            )
        }

        if (!data?.web.isNullOrEmpty()) {
            NavigationRailItem(
                selected = false,
                onClick = { onClickSystems(data.web) },
                icon = { Icon(painter = painterResource(R.drawable.ic_outline_globe_24), contentDescription = null) },
                label = { Text("Sistemas") }
            )
        }

        NavigationRailItem(
            selected = false,
            onClick = onClickSettings,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null
                )
            },
            label = { Text("Config.") }
        )

        Spacer(modifier = Modifier.weight(1f))

        NavigationRailItem(
            selected = false,
            onClick = onClickExit,
            icon = { Icon(imageVector = Icons.AutoMirrored.Default.ExitToApp, contentDescription = null) },
            label = { Text("Sair") }
        )
    }
}

@Preview
@Composable
private fun UPHomeNavigationRailPreview() {
    UPHomeNavigationRail(
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