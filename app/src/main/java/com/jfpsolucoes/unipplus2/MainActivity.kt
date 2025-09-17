package com.jfpsolucoes.unipplus2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jfpsolucoes.unipplus2.core.utils.UPAppSession
import com.jfpsolucoes.unipplus2.modules.signin.ui.signInNavigation
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UPAppSession.initialize(this)

        enableEdgeToEdge()
        setContent {
            UNIPPlus2Theme {
                UPNavigationHost(modifier = Modifier.fillMaxSize())
            }
        }
    }

    @Composable
    private fun UPNavigationHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController()
    ) {
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = "/auth"
            ) {
                signInNavigation()
            }
        }
    }
}