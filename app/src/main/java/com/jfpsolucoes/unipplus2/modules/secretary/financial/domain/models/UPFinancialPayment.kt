package com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models

enum class UPFinancialPaymentMethodType {
    PDF, WEB
}

data class UPFinancialPaymentMethod(
    val type: UPFinancialPaymentMethodType? = null,
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

data class UPFinancialPaymentReceipt(
    val label: String? = null,
    val url: String? = null
)

data class UPFinancialPaymentDetail(
    val items: List<UPFinancialPaymentDetailItem>? = null,
    val label: String? = null
)

data class UPFinancialPaymentDetailItem(
    val label: String? = null,
    val value: String? = null
)

data class UPFinancialPayment(
    val docVl: String? = null,
    val dueDt: String? = null,
    val installment: String? = null,
    val payPlan: String? = null,
    val payVl: String? = null,
    val paymentMethods: List<UPFinancialPaymentMethod>? = null,
    val status: UPFinancialPaymentStatus? = null,
    val type: String? = null,
    val obs: String? = null,
    val receipts: List<UPFinancialPaymentReceipt>? = null,
    val details: List<UPFinancialPaymentDetail>? = null,
    val docDifferenceIndicatorColor: Long? = null,
    val docDifferenceIndicatorIcon: String? = null,
    val docDifferenceVl: String? = null,
)