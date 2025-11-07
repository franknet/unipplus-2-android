package com.jfpsolucoes.unipplus2.core.store.payment

import android.app.Activity
import com.android.billingclient.api.ProductDetails

enum class UPSubscriptionStatus {
    OK, Pendent, Cancelled, Expired, None
}

data class UPSubscription(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    var status: UPSubscriptionStatus = UPSubscriptionStatus.None,
    var googlePayProductDetails: ProductDetails? = null
) {
    fun purchase(context: Activity) {
        googlePayProductDetails?.purchase(context)
    }
}