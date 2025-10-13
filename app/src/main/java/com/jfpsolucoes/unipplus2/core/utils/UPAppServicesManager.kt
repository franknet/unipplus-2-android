package com.jfpsolucoes.unipplus2.core.utils

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.SharedPreferencesManager
import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.core.payment.SubscriptionManagerInstance
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl

object UPAppServicesManager {
    private var initialized = false

    suspend fun initialize(context: AppCompatActivity) {
        if (initialized) return
        initialized = true

        HttpService.initialize(context)

        SubscriptionManagerInstance.initialize(context)

        Firebase.initialize(context)

        RemoteConfigManager.initialize()

        SharedPreferencesManager.initialize(context)

        EncryptedDataBase.initialize(context)

        UPBiometricManagerImpl.initialize(context)

        val adReqConfig = RequestConfiguration.Builder()
            .setTestDeviceIds(listOf("791E913ACCAEBE4D038A0AB99E7C7112")).build()
        MobileAds.setRequestConfiguration(adReqConfig)
        MobileAds.initialize(context)
    }
}