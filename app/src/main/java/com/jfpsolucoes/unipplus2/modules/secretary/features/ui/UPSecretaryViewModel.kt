package com.jfpsolucoes.unipplus2.modules.secretary.features.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.UPGetSecretaryFeaturesUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UPSecretaryViewModel(
    val getSecretaryFeaturesUseCase: UPGetSecretaryFeaturesUseCase = UPGetSecretaryFeaturesUseCase()
): ViewModel() {
    private val _featuresState = getSecretaryFeaturesUseCase()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())
    val featuresState = _featuresState.asStateFlow()

    fun fetchSecretaryFeatures() = viewModelScope.launch {
        getSecretaryFeaturesUseCase().collectToFlow(_featuresState, viewModelScope)
    }

}