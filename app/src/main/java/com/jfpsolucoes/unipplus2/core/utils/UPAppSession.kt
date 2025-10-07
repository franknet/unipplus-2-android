package com.jfpsolucoes.unipplus2.core.utils

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.Firebase
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.initialize
import com.jfpsolucoes.unipplus2.core.database.SharedPreferencesManager
import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.core.payment.SubscriptionManagerInstance
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigManager

object UPAppSession {
    private var initialized = false

    lateinit var appBuildVersion: Pair<Int, String>

    suspend fun initialize(context: Context) {
        if (initialized) return
        initialized = true

        appBuildVersion = getAppVersion(context)

        HttpService.initialize(context)

        SubscriptionManagerInstance.initialize(context)

        Firebase.initialize(context)

        RemoteConfigManager.initialize()

        SharedPreferencesManager.initialize(context)

        val adReqConfig = RequestConfiguration.Builder()
            .setTestDeviceIds(listOf("791E913ACCAEBE4D038A0AB99E7C7112")).build()
        MobileAds.setRequestConfiguration(adReqConfig)
        MobileAds.initialize(context)
    }
}