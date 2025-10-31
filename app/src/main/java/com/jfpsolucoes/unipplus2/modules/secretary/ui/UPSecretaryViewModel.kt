package com.jfpsolucoes.unipplus2.modules.secretary.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.debugPrint
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.modules.secretary.domain.UPGetSecretaryFeaturesUseCase
import com.jfpsolucoes.unipplus2.modules.secretary.domain.models.UPSecretaryFeaturesResponse
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import java.lang.Error

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