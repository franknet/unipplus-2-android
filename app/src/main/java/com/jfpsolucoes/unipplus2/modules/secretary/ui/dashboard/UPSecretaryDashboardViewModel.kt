package com.jfpsolucoes.unipplus2.modules.secretary.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class UPSecretaryDashboardViewModel(
    private val dataBase: EncryptedDataBase = EncryptedDataBase.shared
): ViewModel() {

    private val _storedUserProfile = dataBase.userProfileDao().get().filterNotNull()

    private  val _userProfile = UPUserProfileEntity().mutableStateFlow

    val userProfile = _userProfile.asStateFlow()

    init {
        getUserProfile()
    }

    fun getUserProfile() = viewModelScope.launch {
        _storedUserProfile.collect { userProfile ->
            _userProfile.value = userProfile
        }
    }

}