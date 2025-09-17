package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.core.compose.ForEachIndexed
import com.jfpsolucoes.unipplus2.core.utils.extensions.rememberState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@Composable
fun UPHomeNavigationDrawer(
    data: UPHomeSystemsResponse? = null,
    onSelectSystem: (UPSystem) -> Unit,
    onExitClick: () -> Unit = {}
) {
    var selectedIndex by 0.rememberState

    Column(
        modifier = Modifier.width(300.dp)
    ) {
        VerticalSpacer()

        ForEachIndexed(data?.feature) { feature, index ->
            NavigationDrawerItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    onSelectSystem(feature)
                },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                label = { Text(feature.description.value) }
            )
        }

        VerticalSpacer()

        HorizontalDivider()

        VerticalSpacer()

        ForEachIndexed(data?.web) { web, index ->
            NavigationDrawerItem(
                selected = false,
                onClick = {
                    onSelectSystem(web)
                },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                label = { Text(web.description.value) }
            )
        }

        VerticalSpacer()
    }
}