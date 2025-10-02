package com.jfpsolucoes.unipplus2.modules.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.common.model.UPAppInfo
import com.jfpsolucoes.unipplus2.core.database.SHARED_KEY_APP_INFO
import com.jfpsolucoes.unipplus2.core.database.SharedPreferencesManager
import com.jfpsolucoes.unipplus2.core.payment.SubscriptionManagerInstance
import com.jfpsolucoes.unipplus2.core.payment.ui.UPSubscriptionsView
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.profile.ui.UPProfileView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UPSettingsView(
    modifier: Modifier = Modifier,
    title: String
) {
    val session = SharedPreferencesManager.getObject<UPAppInfo>(SHARED_KEY_APP_INFO)?.session
    val navigator = rememberSupportingPaneScaffoldNavigator<Int>()
    val isMainPaneHidden = navigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Hidden
    val isSupportingPaneHidden = navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden
    val coroutineScope = rememberCoroutineScope()

    if (!isSupportingPaneHidden) {
        LaunchedEffect(Unit) {
            navigator.navigateTo(SupportingPaneScaffoldRole.Supporting, 0)
        }
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
                        Text("Versão ${BuildConfig.VERSION_NAME}")
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
                        Card(onClick = {
                            coroutineScope.launch {
                                navigator.navigateTo(SupportingPaneScaffoldRole.Supporting, 0)
                            }
                        }) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(R.drawable.ic_outline_account_circle_24),
                                    contentDescription = ""
                                )
                                Text(
                                    session?.user?.name.value,
                                    style = MaterialTheme.typography.titleMedium,
                                    overflow = TextOverflow.Ellipsis
                                )

                            }
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