package com.jfpsolucoes.unipplus2.modules.signin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch

class UPSignInViewModel(
    val localDatabase: EncryptedDataBase = EncryptedDataBase.shared,
    val remoteDataBase: UPFirebaseDatabase = UPFirebaseDatabase,
    val postSignInUseCase: UPPostSignInUseCase = UPPostSignInUseCase()
): ViewModel() {
    private val _storedSettings = localDatabase.settingsDao().get()
        .firstOrNullFlow()
        .map { it ?: UPSettingsEntity() }

    private val _storedCredentials = localDatabase.credentialsDao().get()
        .filterNotNull()
        .map(::credentialsChangedListener)

    private val _storedUserProfile = remoteDataBase.userProfile
        .map { it ?: UPUserProfileEntity() }

    private val _screedUIState = _storedUserProfile
        .toUIStateFlow()

    val screenUIState = _screedUIState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UIState.UIStateLoading()
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

    private val _settings = _storedSettings
        .collectAsMutableStateFlow(
            scope = viewModelScope,
            initialValue = UPSettingsEntity()
        )

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

    fun updateRgText(text: String) {
        _rgText.update { text }
        _credentials.update { it.copy(rg = text) }
    }

    fun updatePassword(text: String) {
        _password.update { text }
        _credentials.update { it.copy(password = text) }
    }

    fun updateSettings(settings: UPSettingsEntity) = viewModelScope.launch {
        // Disable biometric if auto sign in is enabled
        if (settings.autoSignIn.and(settings.biometricEnabled)) {
            _settings.update { settings.copy(biometricEnabled = false) }
            return@launch
        }
        _settings.update { settings }
    }

    fun resetSingInState() = viewModelScope.launch {
        _sinInUIState.update { UIState.UIStateNone() }
    }

    fun performSignIn() = viewModelScope.launch {
        val credentials = _credentials.value
        if (credentials.password.isEmpty()) {
            return@launch
        }
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

    private fun credentialsChangedListener(credentials: UPCredentialsEntity): UPCredentialsEntity {
        // Start listening to remote user profile if there is stored credentials
        if (credentials.rg.isNotEmpty()) {
            remoteDataBase.startListeningUser(userRg = credentials.rg)
        }
        return credentials
    }

    fun resetAndShowPasswordField() {
        _settings.update { it.copy(autoSignIn = false) }
        _credentials.update { it.copy(password = "") }
        _passwordFieldVisible.update { true }
    }
    private suspend fun signInStateChangedListener(state: UIState<UPUserProfileEntity>): UIState<UPUserProfileEntity> {
        val settings = _settings.value
        if (state.success) {
            localDatabase.settingsDao().insert(settings)
        }
        if (state.failure) {
            resetAndShowPasswordField()
            setSnackBarMessage(state.error?.message)
        }
        return state
    }
}