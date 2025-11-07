package com.jfpsolucoes.unipplus2.modules.secretary.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigKeys
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UPSecretaryDashboardViewModel(
    dataBase: EncryptedDataBase = EncryptedDataBase.shared,
    remoteConfig: RemoteConfigManager = RemoteConfigManager
): ViewModel() {
    private val _userProfileState = dataBase.userProfileDao().get()
        .map { it ?: UPUserProfileEntity() }
        .collectAsMutableStateFlow(viewModelScope, UPUserProfileEntity())
    val userProfile = _userProfileState.asStateFlow()

    val appReviewEnabled = remoteConfig.getBoolean(RemoteConfigKeys.APP_REVIEW_ENABLED)

}