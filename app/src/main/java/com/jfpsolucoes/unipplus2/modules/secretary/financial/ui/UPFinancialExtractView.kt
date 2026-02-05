package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialBalanceView
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialExtractPaymentBottomSheetView
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
    viewModel: UPFinancialExtractViewModel = viewModel(),
    bottomSheetState: SheetState = rememberModalBottomSheetState()
) {
    val extractUIState by viewModel.extractUIState.collectAsStateWithLifecycle()
    val selectedPayment by viewModel.selectedPayment.collectAsStateWithLifecycle()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }

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
            if (data.extract.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Não há dados!",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                return@UPUIStateScaffold
            }
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
                    data.extract.forEach { extract ->
                        item {
                            Text(
                                text = extract.label.orEmpty(),
                                style = MaterialTheme.typography.titleMedium
                            )
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
                                ),
                                action = {
                                    if (!payment.details.isNullOrEmpty()) {
                                        Icon(
                                            painter = painterResource(R.drawable.ic_outline_more_vert_24),
                                            contentDescription = null
                                        )
                                    }
                                }
                            ) {
                                viewModel.setSelectedPayment(payment)
                                showBottomSheet = true
                            }
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

            if (showBottomSheet) {
                ModalBottomSheet(
                    sheetState = bottomSheetState,
                    onDismissRequest = { showBottomSheet = false }
                ) {
                    UPFinancialExtractPaymentBottomSheetView(
                        payment = selectedPayment
                    )
                }
            }
        }
    )
}