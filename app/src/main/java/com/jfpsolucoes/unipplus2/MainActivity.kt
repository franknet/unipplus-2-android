package com.jfpsolucoes.unipplus2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
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
                UPNavigationHost()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun UPNavigationHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController()
    ) {
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            Scaffold {
                NavHost(
                    navController = navController,
                    startDestination = "/auth"
                ) {
                    signInNavigation()
                }
            }
        }
    }
}