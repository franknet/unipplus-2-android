@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.jfpsolucoes.unipplus2.modules.dashboard.domain.models

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole

object UPDashboardDestination {
    val STUDENT_RECORDS = ThreePaneScaffoldDestinationItem(
        pane = ThreePaneScaffoldRole.Secondary,
        content = UPDashBoardNavigationItem.STUDENT_RECORDS
    )
    val FINANCE = ThreePaneScaffoldDestinationItem(
        pane = ThreePaneScaffoldRole.Secondary,
        content = UPDashBoardNavigationItem.FINANCE
    )
    val ACADEMIC_RECORDS = ThreePaneScaffoldDestinationItem(
        pane = ThreePaneScaffoldRole.Secondary,
        content = UPDashBoardNavigationItem.ACADEMIC_RECORDS
    )
    val NOTIFICATIONS = ThreePaneScaffoldDestinationItem(
        pane = ThreePaneScaffoldRole.Secondary,
        content = UPDashBoardNavigationItem.NOTIFICATIONS
    )
}