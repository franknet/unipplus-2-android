package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.compose.ForEachIndexed
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPFinancialView(
    modifier: Modifier = Modifier,
    viewModel: UPFinancialViewModel = viewModel()
) {
    val featuresUIState by viewModel.featuresUIState.collectAsStateWithLifecycle()
    val featureSelectedIndex by viewModel.featureSelectedIndex.collectAsStateWithLifecycle()
    val periodsState by viewModel.periodsState.collectAsStateWithLifecycle()

//    LaunchedEffect(Unit) {
//        viewModel.fetch()
//    }

    UPUIStateScaffold(
        modifier = modifier,
        state = featuresUIState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Financeiro")
                },
                actions = {
                    periodsState?.let {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = it.first())
                        }
                    }
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
                        top = parentPadding.calculateTopPadding(),
                        bottom = parentPadding.calculateBottomPadding()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ForEachIndexed(data.features) { feature, index ->
                        SegmentedButton(
                            enabled = feature.enabled ?: false,
                            selected = featureSelectedIndex == index,
                            onClick = { viewModel.setSelectedFeatureIndex(index) },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = data.features?.size ?: 0
                            )
                        ) {
                            Text(text = feature.title.orEmpty())
                        }
                    }
                }
                
                when (featureSelectedIndex) {
                    0 -> UPFinancialExtractView(modifier = Modifier.fillMaxSize())
                    1 -> UPFinancialDebtsView(modifier = Modifier.fillMaxSize())
                }
            }
        }
    )
}