package com.jfpsolucoes.unipplus2.core.remoteconfig

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.jfpsolucoes.unipplus2.R

class RemoteConfigKeys {
    companion object {
        const val APP_BUILD_VERSION = "app_build_version"
        const val APP_UPDATE_CHECK_ENABLED = "app_update_check_enabled"
        const val APP_SHARE_ENABLED = "app_share_enabled"
        const val APP_SHARE_TEXT = "app_share_text"
        const val AD_ENABLED = "ad_enabled"
    }
}

object RemoteConfigManager {
    fun initialize() {
        val remoteConfig = Firebase.remoteConfig
        val remoteConfigSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings)
        remoteConfig.apply { fetchAndActivate() }
    }

    fun getString(key: String): String = Firebase.remoteConfig.getString(key)

    fun getInt(key: String): Int = Firebase.remoteConfig.getLong(key).toInt()

    fun getBoolean(key: String): Boolean = Firebase.remoteConfig.getBoolean(key)
}