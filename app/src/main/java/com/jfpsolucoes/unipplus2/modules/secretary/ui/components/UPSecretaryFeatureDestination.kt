package com.jfpsolucoes.unipplus2.modules.secretary.ui.components

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeature
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.ui.UPSecretaryStudentRecordsView

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun UPSecretaryFeatureDestination(
    modifier: Modifier = Modifier,
    navigator: ThreePaneScaffoldNavigator<UPSecretaryFeature>,
    feature: UPSecretaryFeature?
) {
    when (feature?.deeplink) {
        "/secretary/student-records" -> UPSecretaryStudentRecordsView(
            modifier,
            navigator = navigator,
            feature = feature
        )
        "/secretary/financial" -> {}
        else -> {}
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
val UPSecretaryFeatureDestinationItems = listOf<ThreePaneScaffoldDestinationItem<String>>(
    ThreePaneScaffoldDestinationItem(
        ListDetailPaneScaffoldRole.Detail,

    )
)