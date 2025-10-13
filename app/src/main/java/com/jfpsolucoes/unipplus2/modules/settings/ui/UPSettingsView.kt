package com.jfpsolucoes.unipplus2.modules.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableState
import com.jfpsolucoes.unipplus2.modules.profile.ui.UPProfileView
import com.jfpsolucoes.unipplus2.modules.settings.ui.components.UPSettingsBiometricItemView
import com.jfpsolucoes.unipplus2.modules.settings.ui.components.UPSettingsProfileItemView
import com.jfpsolucoes.unipplus2.ui.components.dialogs.UPAlertDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UPSettingsView(
    modifier: Modifier = Modifier,
    title: String,
    viewModel: UPSettingsViewModel = viewModel(),
    biometricManager: UPBiometricManager = UPBiometricManagerImpl
) {
    val navigator = rememberSupportingPaneScaffoldNavigator<Int>()
    val isMainPaneHidden = navigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Hidden
    val isSupportingPaneHidden = navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden
    val coroutineScope = rememberCoroutineScope()
    var biometricSwitchChecked by viewModel.biometricEnabled
    var biometricLaunchCount by viewModel.biometricLaunchCount
    val activity = activity

    if (!isSupportingPaneHidden) {
        LaunchedEffect(Unit) {
            navigator.navigateTo(SupportingPaneScaffoldRole.Supporting, 0)
        }
    }

    LaunchedEffect(biometricLaunchCount) {
        if (biometricLaunchCount == 0) {
            return@LaunchedEffect
        }
        biometricManager.authenticate(
            activity,
            subtitle = activity.getString(R.string.biometric_toggle_subtitle_text),
            onSuccess = {
                viewModel.updateBiometricSettings(true)
            },
            onError = { _, message -> },
            onFailed = { }
        )
    }

    SupportingPaneScaffold(
        modifier = modifier,
        directive = navigator.scaffoldDirective,
        scaffoldState = navigator.scaffoldState,
        mainPane = {
            Scaffold(
                topBar = { TopAppBar(title = { Text(title) }) },
                bottomBar = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Versão ${BuildConfig.VERSION_NAME}"
                        )
                    }
                }
            ) { padding ->
                LazyColumn(modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Student info
                    item {
                        UPSettingsProfileItemView(viewModel.userName) {
                            coroutineScope.launch {
                                navigator.navigateTo(SupportingPaneScaffoldRole.Supporting, 0)
                            }
                        }
                    }

                    if (biometricManager.isBiometricAvailable) {
                        item {
                            UPSettingsBiometricItemView(
                                checked = biometricSwitchChecked,
                                onCheckedChange = viewModel::onBiometricToggle
                            )
                        }
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

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Preview
@Composable
private fun UPSettingsViewPreview() {
    UPSettingsView(title = "")
}