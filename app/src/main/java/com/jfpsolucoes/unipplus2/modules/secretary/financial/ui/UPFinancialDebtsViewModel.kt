package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialDebtsData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPayment
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPaymentMethod
import com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases.UPGetFinancialDebtsUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UPFinancialDebtsViewModel(
    val getFinancialDebtsUseCase: UPGetFinancialDebtsUseCase = UPGetFinancialDebtsUseCase(),
): ViewModel() {
    private val _debtsUIState = MutableSharedFlow<UIState<UPFinancialDebtsData>>()

    val debtsUIState = _debtsUIState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UIState.UIStateLoading()
        )

    val _selectedPayment = MutableStateFlow<UPFinancialPayment?>(null)

    val selectedPayment = _selectedPayment.asStateFlow()

    fun setSelectedPayment(method: UPFinancialPayment?) {
        _selectedPayment.value = method
    }

    fun fetch() = viewModelScope.launch {
        getFinancialDebtsUseCase().collect {
            _debtsUIState.emit(it)
        }
    }
}