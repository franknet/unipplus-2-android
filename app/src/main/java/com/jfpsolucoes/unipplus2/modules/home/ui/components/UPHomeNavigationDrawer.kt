package com.jfpsolucoes.unipplus2.modules.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.UPIcons
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberInComposition")
@Composable
fun UPHomeNavigationDrawer(
    data: UPHomeSystemsResponse? = null,
    onSelectSystem: (UPSystem) -> Unit = {},
    onClickExit: () -> Unit = {}
) {
    var selectedId by rememberSaveable {
        mutableIntStateOf((data?.feature?.first()?.id ?: 0))
    }

    val windowInsets = WindowInsets.systemBars.asPaddingValues()

    Column(
        modifier = Modifier
            .width(320.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .safeDrawingPadding()
        ) {
            item { VerticalSpacer() }

            // Features
            items(items = data?.feature.value) { feature ->
                NavigationDrawerItem(
                    selected = selectedId == feature.id,
                    onClick = {
                        onSelectSystem(feature)
                        selectedId = feature.id ?: 0
                    },
                    icon = { Icon(painter = UPIcons.Outlined.of("ic_globe"), contentDescription = null) },
                    label = { Text(feature.description.value, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                )
            }

            item { VerticalSpacer() }

            item { HorizontalDivider() }

            item { VerticalSpacer() }

            // Web
            items(items = data?.web.value) { web ->
                NavigationDrawerItem(
                    selected = selectedId == web.id,
                    onClick = {
                        onSelectSystem(web)
                        selectedId = web.id ?: 0
                    },
                    icon = { Icon(painter = UPIcons.Outlined.of("ic_globe"), contentDescription = null) },
                    label = { Text(web.description.value, maxLines = 1, overflow = TextOverflow.Ellipsis) }
                )
            }

        }

        NavigationDrawerItem(
            modifier = Modifier.padding(bottom = windowInsets.calculateBottomPadding()),
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
}

@Preview(showSystemUi = true)
@Composable
private fun UPHomeNavigationDrawerPreview() {
    UPHomeNavigationDrawer(
        data = UPHomeSystemsResponse(
            feature = listOf(
                UPSystem(id = 0),
                UPSystem(id = 1),
            ),
            web = listOf(
                UPSystem(id = 2),
                UPSystem(id = 3),
                UPSystem(id = 4),
                UPSystem(id = 5)
            )
        )
    )
}