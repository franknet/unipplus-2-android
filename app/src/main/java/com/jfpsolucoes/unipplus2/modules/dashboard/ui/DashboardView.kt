@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)
@file:SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "ComposableNaming"
)

package com.jfpsolucoes.unipplus2.modules.dashboard.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.utils.extensions.navigateTo
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.dashboard.domain.models.UPDashBoardNavigationItem
import com.jfpsolucoes.unipplus2.modules.dashboard.ui.layout.UPDashboardMainPane
import com.jfpsolucoes.unipplus2.modules.notifications.ui.UPNotificationsView
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.ui.UPAcademicRecordsView
import com.jfpsolucoes.unipplus2.modules.sec.finance.ui.FinanceView
import com.jfpsolucoes.unipplus2.modules.sec.student_records.ui.StudentRecordsView
import com.jfpsolucoes.unipplus2.ui.components.ComposeLambda
import com.jfpsolucoes.unipplus2.ui.components.layout.UPSplitView
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DashboardView(
    modifier: Modifier = Modifier,
    viewModel: UPDashboardViewModel = viewModel()
) {
    val studentProfileUIState by viewModel.studentProfileUIState.collectAsState()
    val notificationsUIState by viewModel.notificationsUIState.collectAsState()

    val scaffoldNavigator = rememberSupportingPaneScaffoldNavigator<UPDashBoardNavigationItem>()
    BackHandler(scaffoldNavigator.canNavigateBack()) {
        scaffoldNavigator.navigateBack()
    }
    UPSplitView(
        navigation = scaffoldNavigator,
        detailsContent = detailsContentFor(scaffoldNavigator.currentDestination?.content)
    ) {
        UPDashboardMainPane(
            navigateTo = scaffoldNavigator::navigateTo,
            studentName = studentProfileUIState.data?.name.value,
            courseName = studentProfileUIState.data?.course?.name.value,
            notificationsCount = notificationsUIState.data?.size ?: 0
        )
    }
}

@Composable
fun detailsContentFor(navigationItem: UPDashBoardNavigationItem?): ComposeLambda = {
    when (navigationItem) {
        UPDashBoardNavigationItem.FINANCE -> FinanceView()
        UPDashBoardNavigationItem.ACADEMIC_RECORDS -> UPAcademicRecordsView()
        UPDashBoardNavigationItem.NOTIFICATIONS -> UPNotificationsView()
        else -> StudentRecordsView()
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardPreview() {
    UNIPPlus2Theme(darkTheme = true) {
        DashboardView()
    }
}