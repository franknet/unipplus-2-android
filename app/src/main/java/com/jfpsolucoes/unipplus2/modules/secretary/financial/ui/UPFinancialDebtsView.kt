package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPFinancialDebtsView(
    modifier: Modifier = Modifier,
    viewModel: UPFinancialDebtsViewModel = viewModel()
) {
    val debtsUIState by viewModel.debtsUIState.collectAsStateWithLifecycle()

    UPUIStateScaffold(
        modifier = modifier,
        state = debtsUIState,
        loadingContent = {
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error) {
                viewModel.fetch()
            }
        },
        content = { parentPadding, data ->

        }
    )
}