package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.jfpsolucoes.unipplus2.modules.settings.UPSettingsView
import com.jfpsolucoes.unipplus2.modules.studentprofile.ui.UPStudentProfileView
import com.jfpsolucoes.unipplus2.modules.subscriptionplans.UPSubscriptionPlansView
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebView
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebViewSettings

fun NavGraphBuilder.homeNavigation(route: String) {
    navigation(HOME_ROUTE, route = route) {
        composable(HOME_ROUTE) {  }

        composable<PortalWebViewSettings> {
            val settings = it.toRoute<PortalWebViewSettings>()
            PortalWebView(
                modifier = Modifier.fillMaxSize(),
                url = settings.url
            )
        }

        composable(HOME_STUDENT_PROFILE_ROUTE) {
            UPStudentProfileView(modifier = Modifier.fillMaxSize())
        }

        composable(HOME_SETTINGS_ROUTE) {
            UPSettingsView(modifier = Modifier.fillMaxSize())
        }

        composable(HOME_SUBSCRIPTION_PLANS_ROUTE) {
            UPSubscriptionPlansView(modifier = Modifier.fillMaxSize())
        }
    }
}