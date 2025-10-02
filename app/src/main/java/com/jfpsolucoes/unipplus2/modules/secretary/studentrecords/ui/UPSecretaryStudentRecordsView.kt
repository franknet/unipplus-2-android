package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeature
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models.UPStudentRecordsDisciplinesResponse
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.UPIcons
import com.jfpsolucoes.unipplus2.ui.components.admob.ADBanner
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineItemAlignment
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineItemView
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebViewSettings
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UPSecretaryStudentRecordsView(
    modifier: Modifier = Modifier,
    viewModel: UPStudentRecordsViewModel = viewModel(),
    feature: UPSecretaryFeature,
    navigationButtonEnabled: Boolean = true,
    onClickBack: () -> Unit
) {
    val disciplinesUIState by viewModel.disciplinesUIState.collectAsState()
    val mainNavigator = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.getDisciplines()
    }

    BackHandler(enabled = navigationButtonEnabled) {
        onClickBack()
    }

    UPUIStateScaffold(
        modifier = modifier,
        state = disciplinesUIState,
        topBar = {
            UPStudentRecordsTopBar(
                title = feature.description.value,
                onClickBack = onClickBack,
                navigationButtonEnabled = navigationButtonEnabled,
                onClickOpenUrl = {
                    val settings = PortalWebViewSettings(url = feature.portalUrl.value)
                    mainNavigator.navigate(route = settings)
                }
            )
        },
        bottomBar = {
            ADBanner(Modifier.fillMaxWidth())
        },
        loadingContent = {
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error) { viewModel.getDisciplines() }
        },
        content = { padding, data ->
            UPStudentRecordsContent(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                    bottom = padding.calculateBottomPadding()
                ),
                data = data
            )
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun UPStudentRecordsTopBar(
    title: String?,
    navigationButtonEnabled: Boolean = true,
    onClickBack: () -> Unit = {},
    onClickOpenUrl: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(title.value) },
        navigationIcon = {
            if (navigationButtonEnabled) {
                IconButton(onClick = onClickBack) {
                    Icon(painter = painterResource(R.drawable.ic_outline_arrow_back_24), contentDescription = "")
                }
            }
        },
        actions = {
            IconButton(onClick = onClickOpenUrl) {
                Icon(painter = UPIcons.Outlined.of("ic_globe"), contentDescription = "")
            }
        }
    )
}

@Composable
fun UPStudentRecordsContent(
    modifier: Modifier = Modifier,
    data: UPStudentRecordsDisciplinesResponse
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val itemViewAlignment = if (data.courseType == "on_site")
            UPDisciplineItemAlignment.Horizontal else UPDisciplineItemAlignment.Vertical

        data.groups?.value?.forEach { group ->
            item { Text(group.label.value, color = MaterialTheme.colorScheme.onBackground) }

            items(items = group.disciplines.value) { discipline ->
                UPDisciplineItemView(
                    title = discipline.name.value,
                    alignment = itemViewAlignment
                ) {
                    discipline.items.value.forEach { item ->
                        item(
                            label = item.label.value,
                            description = item.grade.value,
                            alignment = itemViewAlignment,
                            fraction = Random.nextFloat()
                        )
                    }
                }
            }
        }

        item { VerticalSpacer() }
    }
}