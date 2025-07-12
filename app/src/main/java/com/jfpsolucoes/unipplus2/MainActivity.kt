package com.jfpsolucoes.unipplus2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.FoldingFeature
import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.core.payment.SubscriptionManagerInstance
import com.jfpsolucoes.unipplus2.core.utils.extensions.ScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.getFoldingFeatures
import com.jfpsolucoes.unipplus2.core.utils.extensions.requestScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.isPhone
import com.jfpsolucoes.unipplus2.ui.LocalFoldingFeatures
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.LocalSnackbarState
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual

class MainActivity : AppCompatActivity() {
    var foldingFeatures: List<FoldingFeature>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        HttpService.initialize(this)
        SubscriptionManagerInstance.initialize(this)

        super.onCreate(savedInstanceState)
//
//        val splash = installSplashScreen()
//        splash.setKeepOnScreenCondition { true }


        setContentView(R.layout.activity_main)

//        getFoldingFeatures {
//            foldingFeatures = it
////            splash.setKeepOnScreenCondition { false }
//            setContentView(R.layout.activity_main)
////            setContent {
////                UNIPPlus2Theme {
////                    MainContent()
////                }
////            }
//        }
    }
}

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "CoroutineCreationDuringComposition"
)
@Composable
private fun MainContent() {
    val navController = rememberNavController()
    val snackbarHostState = SnackbarHostState()
    val foldingFeatures = (activity as MainActivity).foldingFeatures

    if (currentWindowAdaptiveInfo().windowSizeClass.isPhone) {
        activity.requestScreenOrientation(ScreenOrientation.PORTRAIT)
    }

    CompositionLocalProvider(
        LocalFoldingFeatures provides foldingFeatures,
        LocalNavController provides navController,
        LocalSnackbarState provides snackbarHostState
    ) {
        Scaffold(
            modifier = Modifier.safeDrawingPadding(),
            snackbarHost = { MainSnackbarHost() }
        ) {
            MainNavigation()
        }
    }
}

@Composable
private fun MainSnackbarHost() {
    SnackbarHost(hostState = LocalSnackbarState.current) {
        val upVisual = it.visuals as UPSnackbarVisual
        Snackbar(
            snackbarData = it,
            containerColor = upVisual.containerColor
        )
    }
}



