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
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resumeWithException

interface SubscriptionManager {
    val purchasesList: MutableStateFlow<List<Purchase>>
    val productsList: Flow<List<ProductDetails>>
    fun querySubscriptions(): Flow<List<ProductDetails>>
    fun queryPurchases(): Flow<List<Purchase>>
    fun launchBillingFlow(context: Activity, productDetails: List<ProductDetails>)
    suspend fun fetch()
}

object SubscriptionManagerInstance: SubscriptionManager {
    private const val SUBSCRIPTIONS_FEATURE = BillingClient.FeatureType.SUBSCRIPTIONS

    private var mBillingClient: BillingClient? = null

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

    override val purchasesList = emptyList<Purchase>().mutableStateFlow

    override val productsList = flow<List<ProductDetails>> {

    }

    fun initialize(context: Context) {
        if (mBillingClient != null) return

        mBillingClient = BillingClient
            .newBuilder(context)
            .enablePendingPurchases(mPendingPurchasesParams)
            .setListener { result, purchases ->
                if (result.responseCode == BillingResponseCode.OK && purchases != null) {
                    purchasesList.value = purchases
                }
            }
            .build()
    }

    private fun billingErrorsHandler(result: BillingResult? = null) {
        val billingResult = result ?: error("Billing result not found")
        when (billingResult.responseCode) {
            BillingResponseCode.FEATURE_NOT_SUPPORTED -> error("Feature not supported")
            BillingResponseCode.SERVICE_DISCONNECTED -> error("Service disconnected")
            BillingResponseCode.SERVICE_UNAVAILABLE -> error("Service unavailable")
            BillingResponseCode.DEVELOPER_ERROR -> error("Developer error")
            BillingResponseCode.ITEM_ALREADY_OWNED -> error("Item already owned")
            BillingResponseCode.ITEM_NOT_OWNED -> error("Item not owned")
            BillingResponseCode.ITEM_UNAVAILABLE -> error("Item unavailable")
            BillingResponseCode.USER_CANCELED -> error("User canceled")
            BillingResponseCode.BILLING_UNAVAILABLE -> error("Billing unavailable")
            else -> {}
        }
    }

    private suspend fun startConnection() {
        val client = mBillingClient ?: error("Billing client not initialized")
        val connResult = client.startConnection()
        billingErrorsHandler(connResult)
        val featureResult = client.isFeatureSupported(SUBSCRIPTIONS_FEATURE)
        billingErrorsHandler(featureResult)
    }

    override fun querySubscriptions() = flow {
        val productDetailsResult = withContext(Dispatchers.IO) {
            startConnection()
            mBillingClient?.queryProductDetails(mProductDetailsParams)
        }
        val billingResult = productDetailsResult?.billingResult ?: error("Billing result not found")
        billingErrorsHandler(billingResult)
        val productDetails = productDetailsResult.productDetailsList ?: emptyList()
        emit(productDetails)
    }

    override fun queryPurchases() = flow {
        val purchasesResult = withContext(Dispatchers.IO) {
            startConnection()
            mBillingClient?.queryPurchasesAsync(mPurchaseParams)
        }
        val billingResult = purchasesResult?.billingResult ?: error("Billing result not found")
        billingErrorsHandler(billingResult)
        emit(purchasesResult.purchasesList)
    }

    override suspend fun fetch() {
        startConnection()
    }

    override fun launchBillingFlow(context: Activity, productDetails: List<ProductDetails>) {
        val billingFlowParams = productDetails.map {
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(it)
                .build()
        }
        mBillingClient?.launchBillingFlow(context, BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(billingFlowParams)
            .build()
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun BillingClient.startConnection(): BillingResult = suspendCancellableCoroutine { conn ->
    if (isReady) { return@suspendCancellableCoroutine }
    startConnection(object : BillingClientStateListener {
        override fun onBillingServiceDisconnected() {
            conn.resumeWithException(Exception("Billing service disconnected"))
        }
        override fun onBillingSetupFinished(result: BillingResult) {
            conn.resume(result) {
                if (isReady) { endConnection() }
            }
        }
    })
}
