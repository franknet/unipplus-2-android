package com.jfpsolucoes.unipplus2.core.ads

import android.app.Activity
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigKeys
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object UPAdManager {
    val adsEnabled = MutableStateFlow(false)

    private val _adEnabledFlow = combine(
        UPFirebaseDatabase.userProfile,
        RemoteConfigManager.getBoolean(RemoteConfigKeys.AD_ENABLED)
    ) { userProfile, adEnabled ->
        val subscriptionActive = userProfile?.preferences?.subscription?.active ?: false
        adEnabled && !subscriptionActive
    }

    fun initialize(context: Activity) {
        val adReqConfig = RequestConfiguration.Builder()
            .setTestDeviceIds(listOf("791E913ACCAEBE4D038A0AB99E7C7112")).build()
        MobileAds.setRequestConfiguration(adReqConfig)
        MobileAds.initialize(context)
        CoroutineScope(Dispatchers.IO).launch {
            _adEnabledFlow.collect(adsEnabled::emit)
        }
    }

}