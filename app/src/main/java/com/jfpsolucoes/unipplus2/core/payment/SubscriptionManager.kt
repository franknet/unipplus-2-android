package com.jfpsolucoes.unipplus2.core.payment

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClientStateListener
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
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

interface UPSubscriptionManager {
    var subscriptions: Flow<UIState<List<UPSubscription>>>

    var hasPurchasedPlan: Flow<Boolean>
}

object SubscriptionManagerInstance: UPSubscriptionManager {
    private var mBillingClient: BillingClient? = null

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
        if (billingResult.responseCode == BillingResponseCode.OK) {
            mPurchasesList.value = purchases.value
        }
    }

    private val mPurchaseUpdateListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingResponseCode.OK) {
            mPurchasesList.value = purchases.value
        }
    }
    private val mQueryProductDetailsListener = ProductDetailsResponseListener { billingResult, productsResult ->
        if (billingResult.responseCode == BillingResponseCode.OK) {
            mProductsList.value = productsResult.productDetailsList
        }
    }

    val purchasesList = mPurchasesList.asStateFlow()
    val productsList = mProductsList.asStateFlow()

    override var subscriptions = productsList.combine(purchasesList) { products, purchases ->
        products.map { product ->
            var sub = UPSubscription(
                product.productId,
                product.name,
                product.description,
                product.subscriptionOfferDetails?.last()?.pricingPhases?.pricingPhaseList?.last()?.formattedPrice.value
            )
            purchases.find { it.products.last() == product.productId }?.let {
                when (it.purchaseState) {

                }
                sub.status = UPSubscriptionStatus.OK
            }
            sub
        }
    }.toUIStateFlow()

    override var hasPurchasedPlan = subscriptions.map { subsState ->
        subsState.data?.find { it.status == UPSubscriptionStatus.OK } != null
    }

    fun initialize(context: Context) {
        if (mBillingClient != null) return

        mBillingClient = BillingClient
            .newBuilder(context)
            .enablePendingPurchases(mPendingPurchasesParams)
            .enableAutoServiceReconnection()
            .setListener(mPurchaseUpdateListener)
            .build()
    }

    private fun startConnection(onConnected: (() -> Unit)? = null) {
        mBillingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {}

            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingResponseCode.OK) {
                    onConnected?.invoke()
                }
            }
        })
    }

    fun queryProductDetails() {
        mBillingClient?.queryProductDetailsAsync(
            mProductDetailsParams,
            mQueryProductDetailsListener
        )
    }

    fun queryPurchases() {
        mBillingClient?.queryPurchasesAsync(
            mPurchaseParams,
            mPurchasesResponseListener
        )
    }

    fun launchBillingFlow(context: Activity, productDetails: List<ProductDetails>) {
        val billingFlowParams = productDetails.map {
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(it)
                .build()
        }
        mBillingClient?.launchBillingFlow(
            context, BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(billingFlowParams)
                .build()
        )
    }
}

fun ProductDetails.purchase(context: Activity) {
    SubscriptionManagerInstance.launchBillingFlow(context, listOf(this))
}
