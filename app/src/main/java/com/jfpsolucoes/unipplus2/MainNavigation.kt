package com.jfpsolucoes.unipplus2

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jfpsolucoes.unipplus2.ui.LocalNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigation() {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = LocalNavController.current,
        startDestination = LOGIN_ROUTE
    ) {

    }
}