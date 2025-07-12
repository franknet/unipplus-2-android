package com.jfpsolucoes.unipplus2.modules.sec.student_records.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.modules.sec.student_records.domain.UPGetStudentRecordsUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class StudentRecordsViewModel(
    getStudentRecordsUseCase: UPGetStudentRecordsUseCase = UPGetStudentRecordsUseCase()
): ViewModel() {
    val studentRecords = getStudentRecordsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UIState.UIStateNone())
}