package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialPaymentBottonSheetView
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialPaymentRow
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebViewSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPFinancialDebtsView(
    modifier: Modifier = Modifier,
    viewModel: UPFinancialDebtsViewModel = viewModel(),
    bottonSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    navController: NavHostController? = LocalNavController.current,
) {
    val debtsUIState by viewModel.debtsUIState.collectAsStateWithLifecycle()

    val paymentSelected by viewModel.selectedPayment.collectAsStateWithLifecycle()

    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }

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
        content = { _, data ->
            LazyColumn {
                // Top spacing
                item {
                    VerticalSpacer()
                }

                items(items = data.debts.orEmpty()) { debtPayment ->
                    val topCorderRadius = if (data.debts?.first() == debtPayment) 16.dp else 0.dp
                    val bottomCorderRadius = if (data.debts?.last() == debtPayment) 16.dp else 0.dp

                    UPFinancialPaymentRow(
                        modifier = Modifier.fillMaxWidth(),
                        payment = debtPayment,
                        shape = RoundedCornerShape(
                            topStart = topCorderRadius,
                            topEnd = topCorderRadius,
                            bottomStart = bottomCorderRadius,
                            bottomEnd = bottomCorderRadius
                        )
                    ) {
                        viewModel.setSelectedPayment(debtPayment)
//                        showBottomSheet = true //TODO: Future implementation
                    }

                    if (data.debts?.last() != debtPayment) {
                        HorizontalDivider()
                    }
                }

                // Bottom spacing
                item {
                    VerticalSpacer()
                }
            }
        }
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = bottonSheetState
        ) {
            UPFinancialPaymentBottonSheetView(
                payment = paymentSelected
            ) { paymentMethod ->
                val portalSettings = PortalWebViewSettings(
                    url = paymentMethod.deepLink.orEmpty()
                )
                showBottomSheet = false
                navController?.navigate(portalSettings)
            }
        }
    }
}