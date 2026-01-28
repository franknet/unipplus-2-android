package com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models
data class UPFinancialDebtsData(
    val courseType: String? = null,
    val debts: List<UPFinancialDebt>? = null
)

data class UPFinancialDebt(
    val docVl: String? = null,
    val dueDt: String? = null,
    val installment: String? = null,
    val payPlan: String? = null,
    val payVl: String? = null,
    val paymentMethods: List<UPFinancialPaymentMethod>? = null,
    val status: UPFinancialPaymentStatus? = null,
    val type: String? = null
)

data class UPFinancialPaymentMethod(
    val deepLink: String? = null,
    val iconSVG: String? = null,
    val id: String? = null,
    val label: String? = null
)

data class UPFinancialPaymentStatus(
    val color: Long? = null,
    val description: String? = null,
    val icon: String? = null
)
