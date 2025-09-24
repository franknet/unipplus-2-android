package com.jfpsolucoes.unipplus2.modules.secretary.ui.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeature
import com.jfpsolucoes.unipplus2.ui.UPIcons
import com.jfpsolucoes.unipplus2.ui.components.admob.ADBanner
import com.jfpsolucoes.unipplus2.ui.components.features.UPFeatureCard
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UPSecretaryDashboardView(
    modifier: Modifier = Modifier,
    features: List<UPSecretaryFeature>?,
    onSelectFeature: (UPSecretaryFeature) -> Unit = {}
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = modifier.padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { VerticalSpacer() }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    item { HorizontalSpacer() }

                    items(features.value) { feature ->

                        UPFeatureCard(
                            modifier = Modifier.width(150.dp),
                            icon = UPIcons.Outlined.of("ic_globe"),
                            label = feature.description.value,
                            enabled = feature.enabled,
                            message = feature.message,
                            onClick = { onSelectFeature(feature) }
                        )
                    }

                    item { HorizontalSpacer() }
                }
            }

            item { ADBanner() }
        }
    }
}