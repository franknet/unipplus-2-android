package com.jfpsolucoes.unipplus2.core.payment

enum class UPSubscriptionStatus {
    OK, Pendent, Cancelled, Expired, None
}

data class UPSubscription(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    var status: UPSubscriptionStatus = UPSubscriptionStatus.None,
)