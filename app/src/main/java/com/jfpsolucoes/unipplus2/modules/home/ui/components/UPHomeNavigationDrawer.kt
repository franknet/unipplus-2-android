package com.jfpsolucoes.unipplus2.modules.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.colors.primaryBackgroundLow
import com.jfpsolucoes.unipplus2.ui.components.image.Image
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberInComposition")
@Composable
fun UPHomeNavigationDrawer(
    modifier: Modifier = Modifier,
    data: UPHomeSystemsResponse? = null,
    selectedSystem: UPSystem? = null,
    onSelectSystem: (UPSystem) -> Unit = {},
    onClickExit: () -> Unit = {},
    shape: Shape = RoundedCornerShape(16.dp)
) {
    val itemColors = NavigationDrawerItemDefaults.colors(
        selectedIconColor = Color.White,
        selectedTextColor = Color.White,
        unselectedIconColor = Color.White.copy(0.7f),
        unselectedTextColor = Color.White.copy(0.7f),
        selectedContainerColor = Color.White.copy(0.2f),
    )

    Surface(
        modifier = Modifier
            .width(320.dp)
            .fillMaxHeight(),
        color = MaterialTheme.colorScheme.primaryBackgroundLow,
        contentColor = Color.White
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                item { VerticalSpacer() }

                // Features
                items(items = data?.feature.value) { feature ->
                    NavigationDrawerItem(
                        selected = selectedSystem?.id == feature.id,
                        onClick = { onSelectSystem(feature) },
                        icon = { Image(
                            modifier = Modifier.size(24.dp),
                            svgString = feature.iconSVG.value,
                            contentDescription = feature.description,
                            color = LocalContentColor.current
                        ) },
                        label = { Text(feature.description.value, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        shape = shape,
                        colors = itemColors
                    )
                }

                item { VerticalSpacer() }

                item { HorizontalDivider(Modifier.background(Color.White)) }

                item { VerticalSpacer() }

                // Web
                items(items = data?.web.value) { web ->
                    NavigationDrawerItem(
                        selected = selectedSystem?.id == web.id,
                        onClick = { onSelectSystem(web) },
                        icon = { Image(
                            modifier = Modifier.size(24.dp),
                            svgString = web.iconSVG.value,
                            contentDescription = web.description,
                            color = LocalContentColor.current
                        ) },
                        label = { Text(web.description.value, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        shape = shape,
                        colors = itemColors
                    )
                }

            }

            NavigationDrawerItem(
                selected = false,
                onClick = onClickExit,
                icon = { Icon(painter = painterResource(R.drawable.ic_outline_exit_to_app_24), contentDescription = null) },
                label = { Text("Sair") },
                colors = itemColors
            )

        }
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