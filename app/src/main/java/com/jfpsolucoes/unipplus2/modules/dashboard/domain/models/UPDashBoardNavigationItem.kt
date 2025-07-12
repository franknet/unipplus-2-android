@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.jfpsolucoes.unipplus2.modules.dashboard.domain.models;

import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.ui.graphics.vector.ImageVector;

enum class UPDashBoardNavigationItem {
    STUDENT_RECORDS,
    FINANCE,
    ACADEMIC_RECORDS,
    NOTIFICATIONS;

    val icon: ImageVector
        get() = when (this) {
            STUDENT_RECORDS -> Icons.Outlined.Edit
            FINANCE -> Icons.Outlined.Edit
            ACADEMIC_RECORDS -> Icons.Outlined.Edit
            NOTIFICATIONS -> Icons.Outlined.Edit
        }

    val label: String
        get() = when (this) {
            STUDENT_RECORDS -> "Notas e faltas"
            FINANCE -> "Financeiro"
            ACADEMIC_RECORDS -> "Histórico Escolar"
            NOTIFICATIONS -> "Notificações"
        }

    val destination: ThreePaneScaffoldDestinationItem<UPDashBoardNavigationItem>
        get() = when (this) {
            STUDENT_RECORDS -> UPDashboardDestination.STUDENT_RECORDS
            FINANCE -> UPDashboardDestination.FINANCE
            ACADEMIC_RECORDS -> UPDashboardDestination.ACADEMIC_RECORDS
            NOTIFICATIONS -> UPDashboardDestination.NOTIFICATIONS
        }

    val enabled: Boolean
        get() = when (this) {
            STUDENT_RECORDS -> true
            FINANCE -> true
            ACADEMIC_RECORDS -> true
            NOTIFICATIONS -> true
        }
}