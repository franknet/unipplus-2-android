package com.jfpsolucoes.unipplus2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.dao.UPSettingsDao
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.utils.UPAppServicesManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.firstOrNullFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.ui.signInNavigation
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateView
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainActivity() : AppCompatActivity() {
    private lateinit var _screenUIState: StateFlow<UIState<UPSettingsEntity?>>

    override fun onCreate(savedInstanceState: Bundle?) {
        UPAppServicesManager.initialize(this@MainActivity)
        _screenUIState = EncryptedDataBase.shared.settingsDao().get()
            .firstOrNullFlow()
            .toUIStateFlow()
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = UIState.UIStateNone()
            )
        installSplashScreen().setKeepOnScreenCondition { _screenUIState.value.loading }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UNIPPlus2Theme {
                UPNavigationHost()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
    @Composable
    private fun UPNavigationHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController()
    ) {
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            val screenUIState by _screenUIState.collectAsStateWithLifecycle()

            val signInStartDestination = when (screenUIState.data?.autoSignIn) {
                true -> HOME_NAVIGATION_ROUTE
                else -> LOGIN_ROUTE
            }

            UPUIStateView(
                state = screenUIState,
                loadingContent = { },
                errorContent = {}
            ) {
                NavHost(
                    modifier = modifier
                        .background(color = colorResource(R.color.surfaceDim_highContrast)),
                    navController = navController,
                    startDestination = "/"
                ) {
                    signInNavigation(signInStartDestination)
                }
            }
        }
    }
}