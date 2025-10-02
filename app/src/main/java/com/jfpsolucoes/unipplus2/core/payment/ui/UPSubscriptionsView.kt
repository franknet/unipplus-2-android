package com.jfpsolucoes.unipplus2.core.payment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.compose.ForEachIndexed
import com.jfpsolucoes.unipplus2.core.payment.SubscriptionManagerInstance
import com.jfpsolucoes.unipplus2.core.payment.UPSubscription
import com.jfpsolucoes.unipplus2.core.payment.UPSubscriptionManager
import com.jfpsolucoes.unipplus2.core.payment.UPSubscriptionStatus
import com.jfpsolucoes.unipplus2.ui.UIState
import com.jfpsolucoes.unipplus2.ui.components.error.UPErrorView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateView
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun UPSubscriptionsView(
    modifier: Modifier = Modifier,
    manager: UPSubscriptionManager = SubscriptionManagerInstance
) {
    val subsUIState by manager.subscriptions.collectAsState(UIState.UIStateLoading())
    val hasPurchasedPlan by manager.hasPurchasedPlan.collectAsState(false)
    Card {
        UPUIStateView(
            state = subsUIState,
            loadingContent = { UPLoadingView() },
            errorContent = { error ->
                UPErrorView(error = error)
            },
            content = { subs ->
                Column(
                    Modifier.padding(16.dp),
                    Arrangement.spacedBy(8.dp)
                ) {
                    ForEachIndexed(subs) { sub, index ->
                        if (index > 0 ) { HorizontalDivider() }
                        Row {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = sub.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (sub.status == UPSubscriptionStatus.OK) {
                                Icon(painter = painterResource(R.drawable.ic_outline_check_circle_24), contentDescription = "")
                            }
                        }
                        Text(sub.description, style = MaterialTheme.typography.bodyMedium)

                    }
                }
            }
        )
    }
}

class UPSubscriptionManagerMock(
    override var subscriptions: Flow<UIState<List<UPSubscription>>> = flowOf(
        UIState.UIStateSuccess(listOf(
            UPSubscription(
                id = "sub_monthly_plan",
                title = "Plano mensal",
                description = "Plano de 30 dias",
                price = "R$ 10,00",
                status = UPSubscriptionStatus.OK
            ),
            UPSubscription(
                id = "sub_annual_plan",
                title = "Plano anual",
                description = "Plano de 365 dias",
                price = "R$ 100,00"
            )
        ))
    ),

    override var hasPurchasedPlan: Flow<Boolean> = flowOf(false)
) : UPSubscriptionManager


@Preview
@Composable
private fun UPSubscriptionsViewPreview() {
    UPSubscriptionsView(Modifier.fillMaxWidth(), UPSubscriptionManagerMock())
}