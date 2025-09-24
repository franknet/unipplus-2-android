package com.jfpsolucoes.unipplus2.modules.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.core.utils.extensions.rememberState
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.UPIcons

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UPHomeNavigationDrawer(
    scrollState: ScrollState = rememberScrollState(),
    data: UPHomeSystemsResponse? = null,
    onSelectSystem: (UPSystem) -> Unit = {},
    onClickSettings: () -> Unit = {},
    onClickExit: () -> Unit = {}
) {
    var selectedSystemId by rememberSaveable {
        mutableIntStateOf((data?.feature?.first()?.id ?: 0))
    }

    Scaffold(
        modifier = Modifier.width(300.dp),
        bottomBar = {
            NavigationDrawerItem(
                selected = false,
                onClick = onClickExit,
                icon = { Icon(imageVector = Icons.AutoMirrored.Default.ExitToApp, contentDescription = null) },
                label = { Text("Sair") },
                shape = RectangleShape,
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .width(300.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data?.feature ?: emptyList()) { feature ->
                NavigationDrawerItem(
                    selected = selectedSystemId == feature.id,
                    onClick = {
                        selectedSystemId = feature.id ?: 0
                        onSelectSystem(feature)
                    },
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    label = { Text(feature.description.value) }
                )
            }

            item {
                NavigationDrawerItem(
                    selected = selectedSystemId == 99,
                    onClick = {
                        selectedSystemId = 99
                        onClickSettings()
                    },
                    icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
                    label = { Text("Configurações") }
                )
            }

            item {
                HorizontalDivider()
            }

            items(data?.web ?: emptyList()) { web ->
                NavigationDrawerItem(
                    selected = selectedSystemId == web.id,
                    onClick = {
                        selectedSystemId = web.id ?: 0
                        onSelectSystem(web)
                    },
                    icon = { Icon(painter = UPIcons.Outlined.of("ic_globe"), contentDescription = null) },
                    label = { Text(web.description.value) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun UPHomeNavigationDrawerPreview() {
    UPHomeNavigationDrawer(
        data = UPHomeSystemsResponse(
            feature = listOf(
                UPSystem(id = 0),
                UPSystem(id = 0),
            ),
            web = listOf(
                UPSystem(id = 0),
                UPSystem(id = 0),
                UPSystem(id = 0),
                UPSystem(id = 0),
                UPSystem(id = 0),
                UPSystem(id = 0),
                UPSystem(id = 0),
                UPSystem(id = 0),
                UPSystem(id = 0),
                UPSystem(id = 0),
                UPSystem(id = 0),
            )
        )
    )
}