package com.jfpsolucoes.unipplus2.modules.sec.academic_records.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain.UPGetAcademicRecordsUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class UPAcademicRecordsViewModel(
    getAcademicRecordsUseCase: UPGetAcademicRecordsUseCase = UPGetAcademicRecordsUseCase()
): ViewModel() {

    val academicRecordsUIState = getAcademicRecordsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UIState.UIStateNone())

}