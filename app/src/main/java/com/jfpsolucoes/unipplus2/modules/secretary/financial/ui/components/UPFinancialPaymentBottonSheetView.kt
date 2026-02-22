package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.material.color.MaterialColors
import com.jfpsolucoes.unipplus2.core.compose.ForEach
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPayment
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPaymentMethod
import com.jfpsolucoes.unipplus2.ui.components.image.Image
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer
import com.jfpsolucoes.unipplus2.ui.components.spacer.VerticalSpacer

@Composable
fun UPFinancialPaymentBottonSheetView(
    modifier: Modifier = Modifier,
    payment: UPFinancialPayment? = null,
    onSelectMethod: (UPFinancialPaymentMethod) -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Meios de Pagamento",
            style = MaterialTheme.typography.titleMedium
        )
        VerticalSpacer()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HorizontalSpacer()

            ForEach(payment?.paymentMethods) { paymentMethod ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FilledIconButton(
                        modifier = Modifier.size(60.dp),
                        shape = CircleShape,
                        onClick = { onSelectMethod(paymentMethod) },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryFixed,
                            contentColor = MaterialTheme.colorScheme.onPrimaryFixed,
                        )
                    ) {
                        Image(
                            modifier = Modifier.padding(12.dp),
                            svgString = paymentMethod.iconSVG.orEmpty(),
                            color = MaterialTheme.colorScheme.onPrimaryFixed
                        )
                    }
                    VerticalSpacer(space = 4.dp)
                    Text(
                        text = paymentMethod.label.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            HorizontalSpacer()
        }
        VerticalSpacer()
    }
}