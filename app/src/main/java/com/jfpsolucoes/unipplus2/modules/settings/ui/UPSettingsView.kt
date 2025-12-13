package com.jfpsolucoes.unipplus2.modules.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.profile.ui.UPProfileView
import com.jfpsolucoes.unipplus2.modules.settings.ui.components.UPSettingsBiometricItemView
import com.jfpsolucoes.unipplus2.modules.settings.ui.components.UPSettingsProfileItemView
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UPSettingsView(
    modifier: Modifier = Modifier,
    title: String,
    viewModel: UPSettingsViewModel = viewModel()
) {
    val activity = activity
    val navigator = rememberSupportingPaneScaffoldNavigator<Int>()
    val isMainPaneHidden = navigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Hidden
    val isSupportingPaneHidden = navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden
    val coroutineScope = rememberCoroutineScope()

    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val biometricAvailable by viewModel.biometricAvailable.collectAsStateWithLifecycle()

    if (!isSupportingPaneHidden) {
        LaunchedEffect(Unit) {
            navigator.navigateTo(SupportingPaneScaffoldRole.Supporting, 0)
        }
    }

    UPUIStateScaffold(
        state = userProfile,
        snackbarHost = {
            SnackbarHost(viewModel.snackbarState) {
                Snackbar(it)
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        loadingContent = {
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error)
        }
    ) { padding, userProfile ->
        SupportingPaneScaffold(
            modifier = modifier,
            directive = navigator.scaffoldDirective.copy(
                verticalPartitionSpacerSize = 0.dp,
                horizontalPartitionSpacerSize = 0.dp
            ),
            scaffoldState = navigator.scaffoldState,
            mainPane = {
                LazyColumn(modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Student info
                    item {
                        UPSettingsProfileItemView(userProfile.user?.name.value) {
                            coroutineScope.launch {
                                navigator.navigateTo(SupportingPaneScaffoldRole.Supporting, 0)
                            }
                        }
                    }

                    if (biometricAvailable) {
                        item {
                            UPSettingsBiometricItemView(
                                biometricChecked = settings.biometricEnabled,
                                onBiometricCheckedChange = {
                                    viewModel.updateBiometricSettings(it, activity)
                                },
                                autoSignChecked = settings.autoSignIn,
                                onAutoSignCheckedChange = viewModel::onAutoSignCheckedChange
                            )
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Versão: ${BuildConfig.VERSION_NAME}"
                            )
                        }
                    }

                }
            },
            supportingPane = {
                navigator.currentDestination?.contentKey?.let {
                    when (it) {
                        0 -> UPProfileView(
                            navigationIconEnabled = isMainPaneHidden
                        ) { coroutineScope.launch {
                            navigator.navigateBack()
                        } }
                        else -> {}
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Preview
@Composable
private fun UPSettingsViewPreview() {
    UPSettingsView(title = "")
}