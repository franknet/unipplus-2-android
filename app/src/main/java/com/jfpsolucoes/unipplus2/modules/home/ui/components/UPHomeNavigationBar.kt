package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jfpsolucoes.unipplus2.core.compose.ForEachIndexed
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer

@Composable
fun UPHomeNavigationBar(
    data: UPHomeSystemsResponse? = null
) {
    NavigationBar {
        ForEachIndexed(data?.feature) { feature, index ->
            NavigationBarItem(
                selected = true,
                onClick = { /*TODO*/ },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                label = {
                    Text(feature.description.value)
                }
            )
        }

        HorizontalSpacer()

        data?.web?.let { web ->
            NavigationBarItem(
                selected = true,
                onClick = { /*TODO*/ },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                label = {
                    Text("Sistemas")
                }
            )
        }

    }
}