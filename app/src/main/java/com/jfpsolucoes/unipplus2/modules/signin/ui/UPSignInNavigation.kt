package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jfpsolucoes.unipplus2.LOGIN_ROUTE
import com.jfpsolucoes.unipplus2.modules.home.ui.homeNavigation

fun NavGraphBuilder.signInNavigation() {
    navigation(LOGIN_ROUTE, route = "/auth") {
        composable(route = LOGIN_ROUTE) {
            UPSignInView()
        }

        homeNavigation()
    }
}