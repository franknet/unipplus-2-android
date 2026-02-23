package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.ads.UPAdManager
import com.jfpsolucoes.unipplus2.core.database.UPFirebaseDatabase
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialDebtsData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPayment
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialPaymentMethod
import com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases.UPGetFinancialDebtsUseCase
import com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases.UPGetFinancialDownloadUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class UPFinancialDebtsViewModel(
    val getFinancialDebtsUseCase: UPGetFinancialDebtsUseCase = UPGetFinancialDebtsUseCase(),
    val getFinancialDownloadUseCase: UPGetFinancialDownloadUseCase = UPGetFinancialDownloadUseCase()
): ViewModel() {
    private val _debtsUIState = MutableSharedFlow<UIState<UPFinancialDebtsData>>()

    val debtsUIState = _debtsUIState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UIState.UIStateLoading()
        )

    private val _selectedPayment = MutableStateFlow<UPFinancialPayment?>(null)

    private val _selectedPaymentMethod = MutableStateFlow<UPFinancialPaymentMethod?>(null)

    private val _downloadedFileState = MutableStateFlow<UIState<File>>(UIState.UIStateNone())

    val selectedPayment = _selectedPayment.asStateFlow()

    val selectedPaymentMethod = _selectedPaymentMethod.asStateFlow()

    val downloadedFileState = _downloadedFileState.asStateFlow()

    val adsEnabled = UPAdManager.adsEnabled.asStateFlow()

    fun setSelectedPayment(method: UPFinancialPayment?) {
        _selectedPayment.value = method
    }

    fun setSelectedPaymentMethod(method: UPFinancialPaymentMethod?) {
        _selectedPaymentMethod.value = method
    }

    fun fetch() = viewModelScope.launch {
        getFinancialDebtsUseCase().collect {
            _debtsUIState.emit(it)
        }
    }

    fun downloadPdfFile(paymentMethod: UPFinancialPaymentMethod) {
        getFinancialDownloadUseCase(paymentMethod.deepLink.orEmpty())
            .collectToFlow(
                flow = _downloadedFileState,
                scope = viewModelScope
            )
    }

    fun resetDownloadedFileState() {
        _downloadedFileState.update { UIState.UIStateNone() }
    }
}