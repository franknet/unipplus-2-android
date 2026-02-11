package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.ads.UPAdManager
import com.jfpsolucoes.unipplus2.core.compose.LazyForEachColumn
import com.jfpsolucoes.unipplus2.core.utils.extensions.ShowInterstitialAd
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.core.utils.extensions.showInterstitialAd
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPaymentMethodType
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialPaymentBottonSheetView
import com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components.UPFinancialPaymentRow
import com.jfpsolucoes.unipplus2.ui.LocalNavController
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import com.jfpsolucoes.unipplus2.ui.components.snackbar.UPSnackbarVisual
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebViewSettings
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPFinancialDebtsView(
    modifier: Modifier = Modifier,
    viewModel: UPFinancialDebtsViewModel = viewModel(),
    bottonSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    snackbarState: SnackbarHostState = SnackbarHostState(),
    navController: NavHostController? = LocalNavController.current,
    mainActivity: Activity? = activity
) {
    val coroutineScope = rememberCoroutineScope()

    val debtsUIState by viewModel.debtsUIState.collectAsStateWithLifecycle()

    val paymentSelected by viewModel.selectedPayment.collectAsStateWithLifecycle()

    val isAdsEnabled by UPAdManager.adsEnabled.collectAsStateWithLifecycle()

    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }

    UPUIStateScaffold(
        modifier = modifier,
        state = debtsUIState,
        snackbarHost = {
            SnackbarHost(snackbarState)
        },
        loadingContent = {
            UPLoadingView()
        },
        errorContent = { _, error ->
            UPErrorView(error = error) {
                viewModel.fetch()
            }
        },
        content = { _, data ->
            if (data.debts.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Não há cobranças!",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                return@UPUIStateScaffold
            }

            LazyForEachColumn(
                showDivider = true,
                items = data.debts,
                header = { VerticalSpacer() },
                footer = { VerticalSpacer() }
            ) { debtPayment ->
                item {
                    val topCorderRadius = if (data.debts.first() == debtPayment) 16.dp else 0.dp
                    val bottomCorderRadius = if (data.debts.last() == debtPayment) 16.dp else 0.dp

                    UPFinancialPaymentRow(
                        modifier = Modifier.fillMaxWidth(),
                        payment = debtPayment,
                        shape = RoundedCornerShape(
                            topStart = topCorderRadius,
                            topEnd = topCorderRadius,
                            bottomStart = bottomCorderRadius,
                            bottomEnd = bottomCorderRadius
                        ),
                        action = {
                            if (!debtPayment.paymentMethods.isNullOrEmpty()) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_outline_more_vert_24),
                                    contentDescription = null
                                )
                            }
                        }
                    ) {
                        debtPayment.paymentMethods?.let {
                            viewModel.setSelectedPayment(debtPayment)
                            showBottomSheet = true
                        }
                    }
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
                if (paymentMethod.type == null) {
                    return@UPFinancialPaymentBottonSheetView
                }
                mainActivity?.showInterstitialAd(isAdsEnabled) { error ->
                    if (error != null) {
                        coroutineScope.launch {
                            showBottomSheet = false
                            snackbarState.showSnackbar(error)
                        }
                        return@showInterstitialAd
                    }
                    when (paymentMethod.type) {
                        UPFinancialPaymentMethodType.PDF -> {

                        }
                        UPFinancialPaymentMethodType.WEB -> {
                            val portalSettings = PortalWebViewSettings(
                                url = paymentMethod.deepLink.orEmpty()
                            )
                            showBottomSheet = false
                            navController?.navigate(portalSettings)
                        }
                    }
                }
            }
        }
    }
}