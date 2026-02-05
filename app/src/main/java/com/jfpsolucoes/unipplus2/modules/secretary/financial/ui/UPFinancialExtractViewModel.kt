package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialExtractData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPayment
import com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases.UPGetFinancialExtractUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class UPFinancialExtractViewModel(
    val getFinancialExtractUseCase: UPGetFinancialExtractUseCase = UPGetFinancialExtractUseCase(),
): ViewModel() {
    private val _extractUIState = MutableSharedFlow<UIState<UPFinancialExtractData>>()
    private val _selectedPayment = MutableSharedFlow<UPFinancialPayment>()
    val extractUIState = _extractUIState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UIState.UIStateLoading()
        )

    val selectedPayment = _selectedPayment
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )

    fun fetch(period: String?) = viewModelScope.launch {
        getFinancialExtractUseCase(period).collect {
            _extractUIState.emit(it)
        }
    }

    fun setSelectedPayment(payment: UPFinancialPayment) = viewModelScope.launch {
        _selectedPayment.emit(payment)
    }
}