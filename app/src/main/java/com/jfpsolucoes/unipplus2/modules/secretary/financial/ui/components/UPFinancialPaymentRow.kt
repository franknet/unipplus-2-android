package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPayment
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPaymentStatus
import com.jfpsolucoes.unipplus2.ui.components.image.Image
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@Composable
private fun PaymentTypeBox(
    modifier: Modifier = Modifier,
    paymentType: String?,
    color: Color = Color.Gray.copy(alpha = 0.25f),
) {
    Box(
        modifier = modifier
            .size(42.dp)
            .background(
                color = color,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = paymentType.orEmpty(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}



@Composable
fun UPFinancialPaymentRow(
    modifier: Modifier = Modifier,
    payment: UPFinancialPayment,
    shape: Shape = RectangleShape,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        shape = shape
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            PaymentTypeBox(paymentType = payment.type)

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(5f)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = payment.dueDt.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    payment.status?.let { status ->
                        HorizontalSpacer(4.dp)

                        Text(
                            modifier = Modifier.padding(bottom = 3.dp),
                            text = status.description.orEmpty(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = payment.docVl.orEmpty(),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 18.sp
                        )
                    )

                    payment.docDifferenceVl?.let {
                        Row(
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                modifier = Modifier.size(10.dp),
                                svgString = payment.docDifferenceIndicatorIcon.orEmpty(),
                                color = Color(
                                    payment.docDifferenceIndicatorColor ?: Color.Black.toColorLong()
                                )
                            )

                            HorizontalSpacer(4.dp)

                            Text(
                                modifier = Modifier.padding(bottom = 2.dp),
                                text = payment.docDifferenceVl,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 10.sp
                                )
                            )

                        }
                    }
                }
            }

            BadgedBox(
                badge = {
                    payment.status?.let { status ->
                        Image(
                            modifier = Modifier.size(16.dp),
                            svgString = status.icon.orEmpty(),
                            color = Color(status.color ?: Color.Black.toArgb().toLong())
                        )
                    }
                }
            ) {
                Text(
                    text = payment.obs.orEmpty(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
private fun UPFinancialPaymentRowPreview() {
    UNIPPlus2Theme(darkTheme = true) {
        Column {
            (0..3).forEach {
                UPFinancialPaymentRow(
                    modifier = Modifier.fillMaxWidth(),
                    payment = UPFinancialPayment(
                        type = "MS",
                        dueDt = "1/1/2026",
                        docVl = "R$ 1,00",
                        docDifferenceVl = "R$ 2,00",
                        obs = "*DEVE",
                        status = UPFinancialPaymentStatus(
                            description = "Teste de messagem ",
                            color = 4293787648
                        )
                    )
                )
            }
        }
    }
}