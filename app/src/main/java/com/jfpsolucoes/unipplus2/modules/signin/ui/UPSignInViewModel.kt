package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.HOME_NAVIGATION_ROUTE
import com.jfpsolucoes.unipplus2.core.analytics.UPAnalyticsManager
import com.jfpsolucoes.unipplus2.core.database.EncryptedDataBase
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.firstOrNullFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.signin.domain.UPPostSignInUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class UPSignInViewModel(
    val localDatabase: EncryptedDataBase = EncryptedDataBase.shared,
    remoteDataBase: UPFirebaseDatabase = UPFirebaseDatabase,
    val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase()
): ViewModel() {
    private val _storedCredentials = localDatabase.credentialsDao().get()
        .filterNotNull()

    private val _storedUserProfile = remoteDataBase.userProfile

    val userProfile = _storedUserProfile
        .asStateFlow()

    private val _screedUIState = localDatabase.settingsDao().get()
        .map { it ?: UPSettingsEntity() }
        .map(::settingsChangeListener)
        .zip(_storedUserProfile, { settings, userProfile ->
            settings to userProfile
        })
        .toUIStateFlow()
        .map(::screenUIStateChangeListener)

    val screenUIState = _screedUIState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UIState.UIStateNone()
        )

    private val _credentials = _storedCredentials
        .collectAsMutableStateFlow(
            scope = viewModelScope,
            initialValue = UPCredentialsEntity()
        )

    private val _rgText = "".mutableStateFlow

    val rgText = _rgText
        .asStateFlow()

    private val _password = "".mutableStateFlow

    val password = _password
        .asStateFlow()

    private val _settings = UPSettingsEntity().mutableStateFlow

    val settings = _settings
        .asStateFlow()

    private val _passwordFieldVisible = _settings
        .map { !it.biometricEnabled }
        .collectAsMutableStateFlow(
            scope = viewModelScope,
            initialValue = true
        )

    val passwordFieldVisible = _passwordFieldVisible
        .asStateFlow()

    private val _sinInUIState = MutableStateFlow<UIState<UPUserProfileEntity>>(UIState.UIStateNone())

    val singInUIState = _sinInUIState
        .asStateFlow()

    private val _snackBarMessage = MutableStateFlow<String?>(null)

    val snackBarMessage = _snackBarMessage
        .asStateFlow()

    private val _navigationRouteDestination = "".mutableStateFlow

    val navigationRouteDestination = _navigationRouteDestination
        .asStateFlow()

    private val _showBiometricAuthentication = false.mutableStateFlow

    val showBiometricAuthentication = _showBiometricAuthentication
        .asStateFlow()

    private val _autoSignInChecked = false.mutableStateFlow

    val autoSignInChecked = _autoSignInChecked
        .asStateFlow()

    fun updateAuthSignInChecked(checked: Boolean) {
        _autoSignInChecked.update { checked }
        _settings.update { it.copy(autoSignIn = checked, biometricEnabled = !checked) }
    }

    fun updateRgText(text: String) {
        _rgText.update { text }
        _credentials.update { it.copy(rg = text) }
    }

    fun updatePassword(text: String) {
        _password.update { text }
        _credentials.update { it.copy(password = text) }
    }

    fun resetSingInState() = viewModelScope.launch {
        _sinInUIState.update { UIState.UIStateNone() }
        _navigationRouteDestination.update { "" }
    }

    fun performSignIn() = viewModelScope.launch {
        val credentials = _credentials.value
        if (credentials.password.isEmpty()) {
            return@launch
        }
        _showBiometricAuthentication.update { false }
        postSignInUseCase(credentials)
            .map(::signInStateChangedListener)
            .collectToFlow(_sinInUIState)
    }

    fun setSnackBarMessage(message: String?) {
        _snackBarMessage.update { message }
    }

    fun trackScreenView() {
        UPAnalyticsManager.trackScreenView("UPSignInView")
    }

    fun resetAndShowPasswordField() {
        _settings.update { it.copy(autoSignIn = false) }
        _credentials.update { it.copy(password = "") }
        _passwordFieldVisible.update { true }
    }
    private fun settingsChangeListener(settings: UPSettingsEntity): UPSettingsEntity {
        _settings.value = settings
        _autoSignInChecked.value = settings.autoSignIn
        _showBiometricAuthentication.value = settings.biometricEnabled
        return settings
    }
    private fun screenUIStateChangeListener(state: UIState<Pair<UPSettingsEntity, UPUserProfileEntity?>>): UIState<Pair<UPSettingsEntity, UPUserProfileEntity?>> {
        state.data?.first?.let { settings ->
            if (settings.autoSignIn) {
                _navigationRouteDestination.update { HOME_NAVIGATION_ROUTE }
            }
        }
        return state
    }

    private suspend fun signInStateChangedListener(state: UIState<UPUserProfileEntity>): UIState<UPUserProfileEntity> {
        val settings = _settings.value
        if (state.success) {
            localDatabase.settingsDao().insert(settings)
            _navigationRouteDestination.update { HOME_NAVIGATION_ROUTE }
        }
        if (state.failure) {
            resetAndShowPasswordField()
            setSnackBarMessage(state.error?.message)
        }
        return state
    }
}