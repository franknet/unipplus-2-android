package com.jfpsolucoes.unipplus2.core.utils

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.core.payment.SubscriptionManagerInstance

object UPAppSession {
    private var initialized = false

    fun initialize(context: Context) {
        if (initialized) return
        initialized = true

        HttpService.initialize(context)
        SubscriptionManagerInstance.initialize(context)
        Firebase.initialize(context)

        val adReqConfig = RequestConfiguration.Builder()
            .setTestDeviceIds(listOf("791E913ACCAEBE4D038A0AB99E7C7112")).build()
        MobileAds.setRequestConfiguration(adReqConfig)
        MobileAds.initialize(context)
    }
}