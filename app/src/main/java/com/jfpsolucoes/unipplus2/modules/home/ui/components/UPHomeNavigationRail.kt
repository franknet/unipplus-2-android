package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.compose.ForEach
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundLow
import com.jfpsolucoes.unipplus2.ui.components.image.Image

@Composable
fun UPHomeNavigationRail(
    modifier: Modifier = Modifier,
    data: UPHomeSystemsResponse? = null,
    selectedSystem: UPSystem? = null,
    onSelectSystem: (UPSystem) -> Unit = {},
    onClickOpenMenu: () -> Unit = {}
) {
    val itemColors = NavigationRailItemDefaults.colors(
        selectedIconColor = Color.White,
        selectedTextColor = Color.White,
        unselectedIconColor = Color.White.copy(0.7f),
        unselectedTextColor = Color.White.copy(0.7f),
        indicatorColor = Color.White.copy(0.2f)
    )

    NavigationRail(
        containerColor = MaterialTheme.colorScheme.primaryBackgroundLow,
        contentColor = Color.White
    ) {
        NavigationRailItem(
            selected = false,
            onClick = onClickOpenMenu,
            icon = { Icon(painter = painterResource(R.drawable.ic_outline_menu_24), contentDescription = null) },
            label = { Text("Menu") },
            colors = itemColors
        )

        ForEach(data?.feature) { feature ->
            NavigationRailItem(
                selected = selectedSystem?.id == feature.id,
                enabled = feature.isEnabled,
                onClick = { onSelectSystem(feature) },
                icon = { Image(
                    modifier = Modifier.size(24.dp),
                    svgString = feature.iconSVG.value,
                    contentDescription = feature.description,
                    color = LocalContentColor.current
                ) },
                label = { Text(feature.description.value ) },
                colors = itemColors
            )
        }

        Spacer(Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun UPHomeNavigationRailPreview() {
    UPHomeNavigationRail(
        data = UPHomeSystemsResponse(
            feature = listOf(UPSystem(id = 0)),
            web = listOf(UPSystem(id = 1))
        )
    )
}