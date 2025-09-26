package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.compose.ForEach
import com.jfpsolucoes.unipplus2.core.compose.ForEachIndexed
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.rememberState
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@Composable
fun UPHomeNavigationRail(
    modifier: Modifier = Modifier,
    data: UPHomeSystemsResponse? = null,
    onSelectSystem: (UPSystem) -> Unit = {},
    onClickSystems: (List<UPSystem>?) -> Unit = {},
    onClickExit: () -> Unit = {}
) {
    var selectedId by 0.saveableMutableState

    NavigationRail(
        modifier = modifier
    ) {
        ForEach(data?.feature) { feature ->
            NavigationRailItem(
                selected = selectedId == feature.id,
                enabled = feature.isEnabled,
                onClick = { onSelectSystem(feature) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                },
                label = { Text(feature.description.value ) }
            )
        }

        Spacer(Modifier.weight(1f))

        NavigationRailItem(
            selected = false,
            onClick = onClickExit,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ExitToApp,
                    contentDescription = null
                )
            },
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