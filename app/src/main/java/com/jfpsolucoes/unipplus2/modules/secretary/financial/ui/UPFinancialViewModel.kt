package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialFeature
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialFeaturesData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases.UPGetFinancialFeaturesUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class UPFinancialViewModel(
    val getFinancialFeaturesUseCase: UPGetFinancialFeaturesUseCase = UPGetFinancialFeaturesUseCase(),
    ): ViewModel() {
    private val _featuresUIState = MutableSharedFlow<UIState<UPFinancialFeaturesData>>()
    private val _featureSelected = MutableStateFlow<UPFinancialFeature?>(null)
    private val _periodSelected = MutableStateFlow<String?>(null)
    val featuresUIState = _featuresUIState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UIState.UIStateNone()
        )
    val periodSelected = _periodSelected.asStateFlow()
    val featureSelected = _featureSelected.asStateFlow()
    fun fetch() = viewModelScope.launch {
        getFinancialFeaturesUseCase().collect {
            _featuresUIState.emit(it)
        }
    }
    fun setSelectedFeature(feature: UPFinancialFeature?) {
        _featureSelected.value = feature
    }

    fun setSelectedPeriod(period: String?) {
        _periodSelected.value = period
    }
}