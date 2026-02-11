package com.jfpsolucoes.unipplus2.modules.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull

class UPProfileViewModel(
    dataBase: UPFirebaseDatabase = UPFirebaseDatabase
): ViewModel() {
    private val _userProfileState = dataBase.userProfile
        .filterNotNull()
        .toUIStateFlow()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())

    val userProfileState = _userProfileState.asStateFlow()
}