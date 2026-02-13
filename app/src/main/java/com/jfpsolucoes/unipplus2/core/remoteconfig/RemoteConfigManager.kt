package com.jfpsolucoes.unipplus2.core.remoteconfig

import android.annotation.SuppressLint
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.jfpsolucoes.unipplus2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object RemoteConfigKeys {
    const val APP_BUILD_VERSION = "app_build_version"
    const val APP_UPDATE_CHECK_ENABLED = "app_update_check_enabled"
    const val APP_SHARE_ENABLED = "app_share_enabled"
    const val APP_SHARE_TEXT = "app_share_text"
    const val APP_REVIEW_ENABLED = "app_review_enabled"
    const val AD_ENABLED = "ad_enabled"
}

object RemoteConfigManager {
    private val _onUpdate = MutableStateFlow(Unit)

    fun initialize() {
        val remoteConfigSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings)
        remoteConfig.fetchAndActivate()
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                remoteConfig.activate().addOnCompleteListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        _onUpdate.emit(Unit)
                    }
                }
            }
            override fun onError(error: FirebaseRemoteConfigException) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getString(key: String): String = Firebase.remoteConfig.getString(key)

    fun getInt(key: String): Int = Firebase.remoteConfig.getLong(key).toInt()

    fun getBoolean(key: String) = _onUpdate
        .map { Firebase.remoteConfig.getBoolean(key) }
}