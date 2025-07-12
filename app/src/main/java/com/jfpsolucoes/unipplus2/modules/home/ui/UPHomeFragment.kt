package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.modules.signin.ui.HomeView
import com.jfpsolucoes.unipplus2.ui.components.fragment.UPComponentFragment

class UPHomeFragment: UPComponentFragment() {
    @Composable
    override fun Content() {
        HomeView(
            viewModel = viewModel(),
            navController = navController()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        println("UPHomeFragment: onDestroy")
    }
}