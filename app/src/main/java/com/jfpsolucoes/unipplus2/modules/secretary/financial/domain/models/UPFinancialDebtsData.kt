package com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models

data class UPFinancialDebtsData(
    val courseType: String? = null,
    val debts: List<UPFinancialPayment>? = null
)
