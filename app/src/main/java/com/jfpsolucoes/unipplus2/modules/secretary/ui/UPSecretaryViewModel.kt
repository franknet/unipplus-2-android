package com.jfpsolucoes.unipplus2.modules.secretary.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.debugPrint
import com.jfpsolucoes.unipplus2.modules.secretary.domain.UPGetSecretaryFeaturesUseCase
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeaturesResponse
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UPSecretaryViewModel(
    val getSecretaryFeaturesUseCase: UPGetSecretaryFeaturesUseCase = UPGetSecretaryFeaturesUseCase()
): ViewModel() {
    private val _featuresState = MutableStateFlow<UIState<UPSecretaryFeaturesResponse>>(UIState.UIStateNone())
    val featuresState = _featuresState.asStateFlow()
    fun fetchSecretaryFeatures() {
        getSecretaryFeaturesUseCase()
            .debugPrint("UPSecretaryViewModel")
            .collectToFlow(_featuresState, viewModelScope)
    }

}