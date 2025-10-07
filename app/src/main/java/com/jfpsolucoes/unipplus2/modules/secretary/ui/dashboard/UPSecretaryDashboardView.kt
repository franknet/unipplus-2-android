package com.jfpsolucoes.unipplus2.modules.secretary.ui.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.core.common.model.UPAppInfo
import com.jfpsolucoes.unipplus2.core.database.SHARED_KEY_APP_INFO
import com.jfpsolucoes.unipplus2.core.database.SharedPreferencesManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeature
import com.jfpsolucoes.unipplus2.ui.components.admob.ADBanner
import com.jfpsolucoes.unipplus2.ui.components.features.UPFeatureCard
import com.jfpsolucoes.unipplus2.ui.components.image.Image
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.components.unipplus.student.UPStudentInfoCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UPSecretaryDashboardView(
    modifier: Modifier = Modifier,
    features: List<UPSecretaryFeature>?,
    onSelectFeature: (UPSecretaryFeature) -> Unit = {}
) {
    val session = SharedPreferencesManager.getObject<UPAppInfo>(SHARED_KEY_APP_INFO)?.session

    Scaffold(
        bottomBar = {
            ADBanner(Modifier.fillMaxWidth())
        }
    ) { paddingValues ->
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item { VerticalSpacer(0.dp) }

            item {
                UPStudentInfoCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    name = session?.user?.name.value,
                    course = session?.academic?.course?.name.value
                )
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    item { HorizontalSpacer(0.dp) }

                    items(features.value) { feature ->
                        UPFeatureCard(
                            modifier = Modifier.width(125.dp),
                            icon = { Image(
                                modifier = Modifier.size(24.dp),
                                svgString = feature.iconSVG.value,
                                contentDescription = feature.description,
                                color = LocalContentColor.current
                            ) },
                            label = feature.description.value,
                            enabled = feature.enabled,
                            message = feature.message,
                            onClick = { onSelectFeature(feature) }
                        )
                    }

                    item { HorizontalSpacer(0.dp) }
                }
            }

            item {
                VerticalSpacer(0.dp)
            }
        }
    }
}