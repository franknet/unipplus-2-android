package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jfpsolucoes.unipplus2.core.compose.ForEach
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialBalance

@Composable
fun UPFinancialBalanceView(
    modifier: Modifier = Modifier,
    balances: List<UPFinancialBalance>
) {
    Row(
        modifier = modifier
    ) {
        ForEach(balances) { balance ->
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = balance.label.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = balance.value.orEmpty(),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UPFinancialBalanceViewPreview() {
    MaterialTheme {
        UPFinancialBalanceView(
            balances = listOf(
                UPFinancialBalance(label = "Saldo", value = "R$ 1.0"),
                UPFinancialBalance(label = "Saldo", value = "R$ 1.0")
            )
        )
    }
}