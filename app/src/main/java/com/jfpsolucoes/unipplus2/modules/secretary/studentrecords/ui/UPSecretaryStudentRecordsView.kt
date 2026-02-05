package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.compose.LazyForEachColumn
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.models.UPSecretaryFeature
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models.UPStudentRecordsDisciplinesResponse
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.ui.components.UPStudentRecordsDisciplineRow
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.ui.components.UPStudentRecordsTopBar
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.UPIcons
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineItemAlignment
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineItemView
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebViewSettings
import com.jfpsolucoes.unipplus2.ui.styles.secondCardColors
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
                webViewButtonEnabled = !feature.portalUrl.isNullOrEmpty(),
                onClickOpenUrl = {
                    val settings = PortalWebViewSettings(url = feature.portalUrl.value)
                    mainNavigator.navigate(route = settings)
                }
            )
        },
        loadingContent = {
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error) { viewModel.getDisciplines() }
        },
        content = { padding, data ->
            if (data.groups.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Notas não lançadas!",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                return@UPUIStateScaffold
            }

            LazyForEachColumn(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = padding.calculateTopPadding(),
                    end = 16.dp
                ),
                items = data.groups
            ) { group ->
                item { VerticalSpacer() }

                item {
                    Text(
                        text = group.label.orEmpty()
                    )
                }

                item { VerticalSpacer() }

                items(items = group.disciplines.orEmpty()) { discipline ->
                    UPStudentRecordsDisciplineRow(
                        modifier = Modifier.fillMaxWidth(),
                        discipline = discipline
                    )
                    if (group.disciplines?.last() != discipline) {
                        VerticalSpacer()
                    }
                }

                // Bottom spacing
                item { VerticalSpacer() }
            }
        }
    )
}
