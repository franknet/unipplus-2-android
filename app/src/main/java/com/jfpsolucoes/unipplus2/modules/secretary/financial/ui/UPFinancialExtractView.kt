package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialBalanceView
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialPaymentRow
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPFinancialExtractView(
    modifier: Modifier = Modifier,
    period: String? = null,
    viewModel: UPFinancialExtractViewModel = viewModel()
) {
    val extractUIState by viewModel.extractUIState.collectAsStateWithLifecycle()

    LaunchedEffect(period) {
        viewModel.fetch(period)
    }

    UPUIStateScaffold(
        modifier = modifier,
        state = extractUIState,
        loadingContent = {
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error) {
                viewModel.fetch(period)
            }
        },
        content = { _, data ->
            Column {
                VerticalSpacer()

                UPFinancialBalanceView(
                    modifier = Modifier.fillMaxWidth(),
                    balances = data.balance.orEmpty()
                )

                VerticalSpacer()

                HorizontalDivider()

                LazyColumn {
                    // Top spacing
                    item {
                        VerticalSpacer()
                    }

                    data.extract?.forEach { extract ->
                        item {
                            Text(text = extract.label.orEmpty())
                        }

                        item {
                            VerticalSpacer()
                        }

                        items(extract.payments.orEmpty()) { payment ->
                            val topCorderRadius = if (extract.payments?.first() == payment) 16.dp else 0.dp
                            val bottomCorderRadius = if (extract.payments?.last() == payment) 16.dp else 0.dp

                            UPFinancialPaymentRow(
                                modifier = Modifier.fillMaxWidth(),
                                payment = payment,
                                shape = RoundedCornerShape(
                                    topStart = topCorderRadius,
                                    topEnd = topCorderRadius,
                                    bottomStart = bottomCorderRadius,
                                    bottomEnd = bottomCorderRadius
                                )
                            )

                            if (extract.payments?.last() != payment) {
                                HorizontalDivider()
                            }
                        }

                        item {
                            VerticalSpacer()
                        }
                    }

                    // Bottom spacing
                    item {
                        VerticalSpacer()
                    }
                }
            }
        }
    )
}