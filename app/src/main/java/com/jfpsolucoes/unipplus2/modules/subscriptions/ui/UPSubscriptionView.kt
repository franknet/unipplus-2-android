package com.jfpsolucoes.unipplus2.modules.subscriptions.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.store.payment.UPSubscription
import com.jfpsolucoes.unipplus2.core.store.payment.UPSubscriptionStatus
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

enum class SubscriptionPlanType {
    MONTHLY, YEARLY
}

data class SubscriptionPlan(
    val type: SubscriptionPlanType,
    val subscription: UPSubscription,
    val features: List<String> = emptyList(),
    val badge: String? = null
)

@Composable
fun UPSubscriptionView(
    modifier: Modifier = Modifier,
    monthlyPlan: SubscriptionPlan? = null,
    yearlyPlan: SubscriptionPlan? = null,
    onPlanSelected: (SubscriptionPlan) -> Unit = {}
) {
    var selectedPlan by remember {
        mutableStateOf<SubscriptionPlanType?>(
            when {
                yearlyPlan?.subscription?.status == UPSubscriptionStatus.OK -> SubscriptionPlanType.YEARLY
                monthlyPlan?.subscription?.status == UPSubscriptionStatus.OK -> SubscriptionPlanType.MONTHLY
                else -> null
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Escolha seu Plano",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Desbloqueie recursos premium e aproveite ao máximo",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        // Yearly Plan
        yearlyPlan?.let { plan ->
            SubscriptionPlanCard(
                plan = plan,
                isSelected = selectedPlan == SubscriptionPlanType.YEARLY,
                onClick = {
                    selectedPlan = SubscriptionPlanType.YEARLY
                    onPlanSelected(plan)
                }
            )
        }

        // Monthly Plan
        monthlyPlan?.let { plan ->
            SubscriptionPlanCard(
                plan = plan,
                isSelected = selectedPlan == SubscriptionPlanType.MONTHLY,
                onClick = {
                    selectedPlan = SubscriptionPlanType.MONTHLY
                    onPlanSelected(plan)
                }
            )
        }
    }
}

@Composable
private fun SubscriptionPlanCard(
    plan: SubscriptionPlan,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val isActive = plan.subscription.status == UPSubscriptionStatus.OK

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isActive) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = when {
                isActive -> MaterialTheme.colorScheme.primaryContainer
                isSelected -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected || isActive) 4.dp else 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row with Title and Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = plan.subscription.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = plan.subscription.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (isActive) {
                    Icon(
                        painter = painterResource(R.drawable.ic_outline_check_circle_24),
                        contentDescription = "Plano Ativo",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Badge
            plan.badge?.let { badge ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = badge,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Price
            Text(
                text = plan.subscription.price,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Features List
            if (plan.features.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    plan.features.forEach { feature ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_outline_check_circle_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = feature,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            // Status Text
            if (isActive) {
                Text(
                    text = "Plano Atual",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Preview
@Preview(showSystemUi = true)
@Composable
private fun UPSubscriptionViewPreview() {
    UNIPPlus2Theme {
        UPSubscriptionView(
            monthlyPlan = SubscriptionPlan(
                type = SubscriptionPlanType.MONTHLY,
                subscription = UPSubscription(
                    id = "monthly_plan",
                    title = "Plano Mensal",
                    description = "Acesso completo por 30 dias",
                    price = "R$ 19,90/mês",
                    status = UPSubscriptionStatus.None
                ),
                features = listOf(
                    "Acesso ilimitado a todos os recursos",
                    "Suporte prioritário",
                    "Sem anúncios",
                    "Atualizações automáticas"
                )
            ),
            yearlyPlan = SubscriptionPlan(
                type = SubscriptionPlanType.YEARLY,
                subscription = UPSubscription(
                    id = "yearly_plan",
                    title = "Plano Anual",
                    description = "Acesso completo por 365 dias",
                    price = "R$ 199,90/ano",
                    status = UPSubscriptionStatus.None
                ),
                features = listOf(
                    "Todos os benefícios do plano mensal",
                    "Economize mais de 15%",
                    "Acesso prioritário a novos recursos",
                    "Suporte VIP 24/7"
                ),
                badge = "MAIS POPULAR"
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UPSubscriptionViewActivePreview() {
    UNIPPlus2Theme {
        UPSubscriptionView(
            monthlyPlan = SubscriptionPlan(
                type = SubscriptionPlanType.MONTHLY,
                subscription = UPSubscription(
                    id = "monthly_plan",
                    title = "Plano Mensal",
                    description = "Acesso completo por 30 dias",
                    price = "R$ 19,90/mês",
                    status = UPSubscriptionStatus.None
                ),
                features = listOf(
                    "Acesso ilimitado a todos os recursos",
                    "Suporte prioritário",
                    "Sem anúncios"
                )
            ),
            yearlyPlan = SubscriptionPlan(
                type = SubscriptionPlanType.YEARLY,
                subscription = UPSubscription(
                    id = "yearly_plan",
                    title = "Plano Anual",
                    description = "Acesso completo por 365 dias",
                    price = "R$ 199,90/ano",
                    status = UPSubscriptionStatus.OK
                ),
                features = listOf(
                    "Todos os benefícios do plano mensal",
                    "Economize mais de 15%",
                    "Suporte VIP 24/7"
                ),
                badge = "MAIS POPULAR"
            )
        )
    }
}