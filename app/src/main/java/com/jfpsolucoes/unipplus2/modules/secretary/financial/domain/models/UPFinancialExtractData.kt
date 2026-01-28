package com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models

data class UPFinancialExtractData(
    val balance: List<Balance>? = null,
    val courseType: String? = null,
    val extract: List<Extract>? = null
)

data class Balance(
    val label: String? = null,
    val value: String? = null
)

data class Extract(
    val label: String? = null,
    val payments: List<Payment>? = null
)

data class Payment(
    val details: List<Detail>? = null,
    val docDifferenceIndicatorColor: Long? = null,
    val docDifferenceIndicatorIcon: String? = null,
    val docDifferenceVl: String? = null,
    val docVl: String? = null,
    val dueDt: String? = null,
    val obs: String? = null,
    val receipts: List<Receipt>? = null,
    val type: String? = null
)

data class Detail(
    val items: List<Item>? = null,
    val label: String? = null
)

data class Receipt(
    val label: String? = null,
    val url: String? = null
)

data class Item(
    val label: String? = null,
    val value: String? = null
)
