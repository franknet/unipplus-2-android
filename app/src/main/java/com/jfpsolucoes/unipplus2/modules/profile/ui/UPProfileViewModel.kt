package com.jfpsolucoes.unipplus2.modules.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class UPProfileViewModel(
    dataBase: EncryptedDataBase = EncryptedDataBase.shared
): ViewModel() {
    private val _userProfileState = dataBase.userProfileDao().get()
        .map { it ?: UPUserProfileEntity() }
        .toUIStateFlow()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())

    val userProfileState = _userProfileState.asStateFlow()
}