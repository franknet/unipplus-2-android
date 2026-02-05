package com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models

data class UPFinancialExtractData(
    val balance: List<UPFinancialBalance>? = null,
    val courseType: String? = null,
    val extract: List<UPFinancialExtract>? = null
)

data class UPFinancialBalance(
    val label: String? = null,
    val value: String? = null
)

data class UPFinancialExtract(
    val label: String? = null,
    val payments: List<UPFinancialPayment>? = null
)
