package com.jfpsolucoes.unipplus2.modules.secretary.features.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigKeys
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull

class UPSecretaryDashboardViewModel(
    dataBase: UPFirebaseDatabase = UPFirebaseDatabase,
    remoteConfig: RemoteConfigManager = RemoteConfigManager
): ViewModel() {
    private val _userProfileState = dataBase.userProfile
        .filterNotNull()
        .collectAsMutableStateFlow(viewModelScope, UPUserProfileEntity())
    val userProfile = _userProfileState.asStateFlow()

    val appReviewEnabled = remoteConfig.getBoolean(RemoteConfigKeys.APP_REVIEW_ENABLED)
        .collectAsMutableStateFlow(viewModelScope, false)

}