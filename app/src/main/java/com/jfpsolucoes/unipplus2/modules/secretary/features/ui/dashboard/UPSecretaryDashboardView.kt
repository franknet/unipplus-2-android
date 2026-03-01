package com.jfpsolucoes.unipplus2.modules.secretary.features.ui.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.models.UPSecretaryFeature
import com.jfpsolucoes.unipplus2.ui.components.cards.ReviewCard
import com.jfpsolucoes.unipplus2.ui.components.features.UPFeatureCard
import com.jfpsolucoes.unipplus2.ui.components.image.Image
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.components.unipplus.student.UPStudentInfoCard
import com.jfpsolucoes.unipplus2.ui.styles.CardColorsPrimary
import com.jfpsolucoes.unipplus2.ui.styles.secondCardColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UPSecretaryDashboardView(
    modifier: Modifier = Modifier,
    viewModel: UPSecretaryDashboardViewModel = viewModel(),
    features: List<UPSecretaryFeature>?,
    onSelectFeature: (UPSecretaryFeature) -> Unit = {}
) {
    val activity = activity
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val appReviewEnabled by viewModel.appReviewEnabled.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(top = padding.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { VerticalSpacer(0.dp) }

            item {
                UPStudentInfoCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    name = userProfile.name.value,
                    course = userProfile.academic?.course?.name.value,
                    colors = CardColorsPrimary
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
                            colors = secondCardColors,
                            onClick = { onSelectFeature(feature) }
                        )
                    }

                    item { HorizontalSpacer(0.dp) }
                }
            }

            if (appReviewEnabled) {
                item {
                    ReviewCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        colors = secondCardColors
                    )
                }
            }

            item { VerticalSpacer(0.dp) }
        }
    }
}