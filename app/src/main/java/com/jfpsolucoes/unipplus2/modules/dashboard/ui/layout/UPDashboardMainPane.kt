package com.jfpsolucoes.unipplus2.modules.dashboard.ui.layout

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.modules.dashboard.domain.models.UPDashBoardNavigationItem
import com.jfpsolucoes.unipplus2.modules.dashboard.ui.components.UPDashboardClassesSection
import com.jfpsolucoes.unipplus2.modules.dashboard.ui.components.UPDashboardFeaturesRow
import com.jfpsolucoes.unipplus2.modules.dashboard.ui.components.UPDashboardStudentStatusCard
import com.jfpsolucoes.unipplus2.modules.dashboard.ui.components.UPDashboardTasksSection

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun UPDashboardMainPane(
    scrollState: ScrollState = rememberScrollState(),
    studentName: String = "",
    courseName: String = "",
    notificationsCount: Int = 0,
    navigateTo: (ThreePaneScaffoldDestinationItem<UPDashBoardNavigationItem>) -> Unit = {},
) = Column(
    modifier = Modifier.verticalScroll(scrollState),
    verticalArrangement = Arrangement.spacedBy(16.dp),
) {
    UPDashboardStudentStatusCard(
        modifier = Modifier.padding(top = 16.dp),
        fullName = studentName,
        courseName = courseName,
        notificationsCount = notificationsCount,
        onClickNotifications = navigateTo
    )

    UPDashboardFeaturesRow(onClick = navigateTo)

    UPDashboardClassesSection {

    }

    UPDashboardTasksSection {

    }
}