package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.HOME_ROUTE
import com.jfpsolucoes.unipplus2.LOGIN_ROUTE
import com.jfpsolucoes.unipplus2.modules.signin.ui.UPSignInView

fun NavGraphBuilder.homeNavigation() {
    navigation(HOME_ROUTE, route = HOME_NAVIGATION_ROUTE) {
        composable(route = HOME_ROUTE) {
            UPHomeView()
        }
    }
}