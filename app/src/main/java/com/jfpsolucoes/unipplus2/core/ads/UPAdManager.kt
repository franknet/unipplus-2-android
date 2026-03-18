package com.jfpsolucoes.unipplus2.core.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import com.facebook.ads.AdSettings
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigKeys
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

object UPAdManager {

    private val _adEnabledFlow = combine(
        UPFirebaseDatabase.userProfile,
        RemoteConfigManager.getBoolean(RemoteConfigKeys.AD_ENABLED)
    ) { userProfile, adEnabled ->
        val subscriptionActive = userProfile?.preferences?.subscription?.active ?: false
        adEnabled && !subscriptionActive
    }

    val adsEnabled = _adEnabledFlow.stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = SharingStarted.Eagerly,
        initialValue = true
    )

    fun initialize(context: Activity) {
        val adReqConfig = RequestConfiguration.Builder().setTestDeviceIds(listOf("DE5B79B15A493C1572129ADC7E84C9F6")).build()
        AdSettings.addTestDevice("bb25fc68-7a4e-44ce-8118-322e60f3acbe")
        MobileAds.setRequestConfiguration(adReqConfig)
        MobileAds.initialize(context)
    }

    fun showInterstitialAd(
        context: Activity,
        onLoading: ((Boolean) -> Unit)? = null,
        onError: ((error: String?) -> Unit)? = null,
        onSuccess: (() -> Unit)? = null,
    ) {
        if (!adsEnabled.value) {
            onSuccess?.invoke()
            return
        }
        onLoading?.invoke(true)
        InterstitialAd.load(
            context,
            context.getString(R.string.admob_interstitial_id),
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    onLoading?.invoke(false)
                    when (p0.code) {
                        3 -> { // No Fill
                            onSuccess?.invoke()
                        }
                        else -> {
                            onError?.invoke("Ocorreu um erro ao carregar o anúncio!")
                        }
                    }
                    Log.e("InterstitialAd", "$p0 ${p0.code}: ${p0.message}")
                }
                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    onLoading?.invoke(false)
                    ad.show(context)
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            onSuccess?.invoke()
                        }
                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            when (p0.code) {
                                3 -> { // No Fill
                                    onSuccess?.invoke()
                                }
                                else -> {
                                    onError?.invoke("Ocorreu um erro ao mostrar o anúncio!")
                                }
                            }
                            Log.e("InterstitialAd", "$p0 ${p0.code}: ${p0.message}")
                        }
                    }
                }
            }
        )
    }
}