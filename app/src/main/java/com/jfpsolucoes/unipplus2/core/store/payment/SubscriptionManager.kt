package com.jfpsolucoes.unipplus2.core.store.payment

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesResponseListener
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

interface UPSubscriptionManager {
    var subscriptions: Flow<List<UPSubscription>>
}

object SubscriptionManagerInstance: UPSubscriptionManager {
    private var mBillingClient: BillingClient? = null

    private val mBillingResult = MutableStateFlow<BillingResult?>(null)

    private val mPurchasesList = emptyList<Purchase>().mutableStateFlow

    private val mProductsList = emptyList<ProductDetails>().mutableStateFlow

    private val mProductsDetails = listOf(
        QueryProductDetailsParams.Product.newBuilder()
            .setProductId("sub_monthly_plan")
            .setProductType(BillingClient.ProductType.SUBS)
            .build(),
        QueryProductDetailsParams.Product.newBuilder()
            .setProductId("sub_annual_plan")
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
    )

    private val mProductDetailsParams = QueryProductDetailsParams.newBuilder()
        .setProductList(mProductsDetails)
        .build()

    private val mPurchaseParams = QueryPurchasesParams.newBuilder()
        .setProductType(BillingClient.ProductType.SUBS)
        .build()

    private val mPendingPurchasesParams = PendingPurchasesParams.newBuilder()
        .enableOneTimeProducts()
        .build()

    private val mPurchasesResponseListener = PurchasesResponseListener { billingResult, purchases ->
        mBillingResult.value = billingResult
        if (billingResult.responseCode == BillingResponseCode.OK) {
            mPurchasesList.value = purchases.value
        }
    }

    private val mPurchaseUpdateListener = PurchasesUpdatedListener { billingResult, purchases ->
        mBillingResult.value = billingResult
        if (billingResult.responseCode == BillingResponseCode.OK) {
            mPurchasesList.value = purchases.value
        }
    }
    private val mQueryProductDetailsListener = ProductDetailsResponseListener { billingResult, productsResult ->
        mBillingResult.value = billingResult
        if (billingResult.responseCode == BillingResponseCode.OK) {
            mProductsList.value = productsResult.productDetailsList
        }
    }

    override var subscriptions = combine(
        mBillingResult,
        mPurchasesList,
        mProductsList
    ) { result, purchases, products ->
        if (result?.responseCode == BillingResponseCode.OK) {
            throw Error(result.debugMessage)
        }
        products.map { product ->
            UPSubscription(
                id = product.productId,
                title = product.name,
                description = product.description,
                price = product.subscriptionOfferDetails?.last()?.pricingPhases?.pricingPhaseList?.last()?.formattedPrice.value,
                status = UPSubscriptionStatus.None,
                googlePayProductDetails = product
            )
        }
    }

    fun initialize(context: Context) {
        if (mBillingClient != null) return

        mBillingClient = BillingClient
            .newBuilder(context)
            .enablePendingPurchases(mPendingPurchasesParams)
            .enableAutoServiceReconnection()
            .setListener(mPurchaseUpdateListener)
            .build()

        mBillingClient?.queryProductDetailsAsync(
            mProductDetailsParams,
            mQueryProductDetailsListener
        )
    }

    fun launchBillingFlow(
        context: Activity,
        productDetails: List<ProductDetails>,
        onError: ((Throwable) -> Unit)? = null
    ) {
        val billingFlowParams = productDetails.map {
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(it)
                .setOfferToken(it.subscriptionOfferDetails?.last()?.offerToken.value)
                .build()
        }
        val result = mBillingClient?.launchBillingFlow(
            context, BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(billingFlowParams)
                .build()
        )
    }
}

fun ProductDetails.purchase(context: Activity) {
    SubscriptionManagerInstance.launchBillingFlow(context, listOf(this))
}
