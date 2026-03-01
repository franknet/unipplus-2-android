package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialDeepLinks
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialPeriodsBottonSheetView
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialSegmentedButton
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialTopbar
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPFinancialView(
    modifier: Modifier = Modifier,
    title: String,
    viewModel: UPFinancialViewModel = viewModel(),
    navigationButtonEnabled: Boolean = true,
    bottomSheetState: SheetState = rememberModalBottomSheetState(),
    onClickBack: () -> Unit
) {
    val featuresUIState by viewModel.featuresUIState.collectAsStateWithLifecycle()
    val featureSelected by viewModel.featureSelected.collectAsStateWithLifecycle()
    val periodSelected by viewModel.periodSelected.collectAsStateWithLifecycle()
    var showPeriodsBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }

    featuresUIState.data?.periods?.let { periods ->
        if (periodSelected == null) {
            viewModel.setSelectedPeriod(periods.firstOrNull())
        }
    }
    featuresUIState.data?.features?.let { features ->
        if (featureSelected == null) {
            viewModel.setSelectedFeature(features.firstOrNull())
        }
    }

    BackHandler {
        onClickBack.invoke()
    }

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }

    UPUIStateScaffold(
        modifier = modifier,
        state = featuresUIState,
        topBar = {
            UPFinancialTopbar(
                navigationEnabled = navigationButtonEnabled,
                onClickBack = onClickBack,
                title = title,
                periodSelected = periodSelected,
                openPeriodsBottomSheet = {
                    showPeriodsBottomSheet = true
                }
            )
        },
        loadingContent = {
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error) {
                viewModel.fetch()
            }
        },
        content = { parentPadding, data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 16.dp,
                        top = parentPadding.calculateTopPadding(),
                        end = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UPFinancialSegmentedButton(
                    modifier = Modifier.fillMaxWidth(),
                    features = data.features.value,
                    selectedIndex = data.features?.indexOf(featureSelected) ?: 0,
                    onSelectFeature = viewModel::setSelectedFeature
                )

                when (featureSelected?.deepLink) {
                    UPFinancialDeepLinks.EXTRACT -> UPFinancialExtractView(period = periodSelected)
                    UPFinancialDeepLinks.DEBTS -> UPFinancialDebtsView()
                    else -> {}
                }
            }

            if (showPeriodsBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showPeriodsBottomSheet = false },
                    sheetState = bottomSheetState
                ) {
                    UPFinancialPeriodsBottonSheetView(
                        periods = data.periods,
                        selectedPeriod = periodSelected,
                        onSelectPeriod = {
                            showPeriodsBottomSheet = false
                            viewModel.setSelectedPeriod(it)
                        }
                    )
                }
            }
        }
    )
}