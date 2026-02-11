package com.jfpsolucoes.unipplus2.core.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.jfpsolucoes.unipplus2.core.ads.UPAdManager
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.file.UPFileDirectoryManager
import com.jfpsolucoes.unipplus2.core.networking.HttpService
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigManager
import com.jfpsolucoes.unipplus2.core.security.UPBiometricManagerImpl
import com.jfpsolucoes.unipplus2.core.store.UPAppStoreManager
import com.jfpsolucoes.unipplus2.core.store.payment.SubscriptionManagerInstance

object UPAppServicesManager {
    private var initialized = false

    fun initialize(context: AppCompatActivity) {
        if (initialized) return
        initialized = true

        initGoogleAndFirebaseServices(context)

        HttpService.initialize(context)

        EncryptedDataBase.initialize(context)

        UPBiometricManagerImpl.initialize(context)

        UPFileDirectoryManager.initialize(context)
    }

    private fun initGoogleAndFirebaseServices(context: Activity) {
        // Firebase
        Firebase.initialize(context)

        RemoteConfigManager.initialize()

        UPAdManager.initialize(context)

        // Google
        SubscriptionManagerInstance.initialize(context)

        UPAppStoreManager.checkUpdate(context)

        UPFirebaseDatabase.initialize()
    }
}