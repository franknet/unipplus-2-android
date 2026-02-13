package com.jfpsolucoes.unipplus2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jfpsolucoes.unipplus2.core.utils.UPAppServicesManager
import com.jfpsolucoes.unipplus2.modules.signin.ui.signInNavigation
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.LocalSignInState
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        UPAppServicesManager.initialize(this@MainActivity)
        installSplashScreen()
        super.onCreate(savedInstanceState)

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
            NavHost(
                modifier = modifier
                    .background(color = colorResource(R.color.surfaceDim_highContrast)),
                navController = navController,
                startDestination = "/"
            ) {
                signInNavigation()
            }
        }
    }
}