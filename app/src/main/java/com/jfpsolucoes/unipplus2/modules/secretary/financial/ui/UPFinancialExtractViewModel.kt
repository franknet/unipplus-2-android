package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases.UPGetFinancialExtractUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UPFinancialExtractViewModel(
    getFinancialExtractUseCase: UPGetFinancialExtractUseCase = UPGetFinancialExtractUseCase(),
): ViewModel() {
    private val _extractUIState = getFinancialExtractUseCase()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1,
        )
    val extractUIState = _extractUIState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UIState.UIStateNone()
        )

    fun fetch() = viewModelScope.launch {
        _extractUIState.collect()
    }
}