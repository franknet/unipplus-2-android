package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.debugPrint
import com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases.UPGetFinancialDownloadUseCase
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UPSignInViewModel(
    val localDatabase: EncryptedDataBase = EncryptedDataBase.shared,
    val remoteDataBase: UPFirebaseDatabase = UPFirebaseDatabase,
    val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase()
): ViewModel() {

    private val _credentials = localDatabase.credentialsDao().get()
        .filterNotNull()
        .map(::credentialsChangedListener)
        .collectAsMutableStateFlow(
            scope = viewModelScope,
            initialValue = UPCredentialsEntity()
        )

    private val _settings = localDatabase.settingsDao().get()
        .filterNotNull()
        .map(::settingsChangeListener)
        .collectAsMutableStateFlow(
            scope = viewModelScope,
            initialValue = UPSettingsEntity()
        )

    private val _userProfile = remoteDataBase.userProfile

    private val _sinInUIState = MutableStateFlow<UIState<UPUserProfileEntity>>(UIState.UIStateNone())

    val credentials = _credentials.asStateFlow()

    val settings = _settings
        .asStateFlow()

    val userProfile = _userProfile
        .asStateFlow()

    val singInUIState = _sinInUIState.asStateFlow()

    fun updateCredentials(credentials: UPCredentialsEntity) = viewModelScope.launch {
        _credentials.update { credentials }
    }

    fun updateSettings(settings: UPSettingsEntity) = viewModelScope.launch {
        // Prevent auto signIn with empty password
        if (settings.autoSignIn && credentials.value.password.isEmpty()) {
            return@launch
        }
        _settings.update { settings }
    }

    fun resetSingInState() = viewModelScope.launch {
        _sinInUIState.emit(UIState.UIStateNone())
    }

    fun performSignIn() = viewModelScope.launch {
        postSignInUseCase(_credentials.value)
            .map(::sinInStateChangedListener)
            .collectToFlow(_sinInUIState)
    }

    private fun credentialsChangedListener(credentials: UPCredentialsEntity): UPCredentialsEntity {
        // Start listening to remote user profile if there is stored credentials
        if (!credentials.rg.isEmpty() && _userProfile.value == null) {
            remoteDataBase.startListeningUser(userRg = credentials.rg)
        }
        return credentials
    }

    private suspend fun settingsChangeListener(settings: UPSettingsEntity): UPSettingsEntity {
        // Disable biometric if auto sign in is enabled
        if (settings.autoSignIn) {
            localDatabase.settingsDao().insert(settings.copy(biometricEnabled = false))
        }
        return settings
    }

    private suspend fun sinInStateChangedListener(state: UIState<UPUserProfileEntity>): UIState<UPUserProfileEntity> {
        if (state.success) {
            localDatabase.settingsDao().insert(settings.value.copy(signedIn = true))
            localDatabase.credentialsDao().insert(_credentials.value)
        }
        return state
    }
}