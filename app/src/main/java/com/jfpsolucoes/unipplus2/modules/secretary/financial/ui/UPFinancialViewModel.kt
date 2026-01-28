package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases.UPGetFinancialFeaturesUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class UPFinancialViewModel(
    val getFinancialFeaturesUseCase: UPGetFinancialFeaturesUseCase = UPGetFinancialFeaturesUseCase(),
    ): ViewModel() {

    private val _featuresUIState = getFinancialFeaturesUseCase()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1,
        )
    val featuresUIState = _featuresUIState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UIState.UIStateNone()
        )

    val periodsState = _featuresUIState
        .mapLatest { it.data?.periods }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _featureSelectedIndex = MutableStateFlow(0)
    val featureSelectedIndex = _featureSelectedIndex.asStateFlow()

    fun fetch() = viewModelScope.launch {
        _featuresUIState.collect()
    }

    fun setSelectedFeatureIndex(index: Int) {
        _featureSelectedIndex.value = index
    }
}