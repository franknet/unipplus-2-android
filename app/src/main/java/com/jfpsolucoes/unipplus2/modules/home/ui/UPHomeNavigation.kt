package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.HOME_PORTAL_WEB_ROUTE
import com.jfpsolucoes.unipplus2.HOME_ROUTE
import com.jfpsolucoes.unipplus2.LOGIN_ROUTE
import com.jfpsolucoes.unipplus2.modules.signin.ui.UPSignInView
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebView
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebViewSettings

fun NavGraphBuilder.homeNavigation() {
    navigation(HOME_ROUTE, route = HOME_NAVIGATION_ROUTE) {
        composable(route = HOME_ROUTE) {
            UPHomeView()
        }

        composable<PortalWebViewSettings> {
            val settings = it.toRoute<PortalWebViewSettings>()
            val mainNavigator = LocalNavController.current
            PortalWebView(webSettings = settings) {
                mainNavigator.popBackStack()
            }
        }
    }
}