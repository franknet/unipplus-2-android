package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectToFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.debugPrint
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.UPStudentRecordsGetDisciplinesUseCase
import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models.UPStudentRecordsDisciplinesResponse
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UPStudentRecordsViewModel(
    val getDisciplinesUseCase: UPStudentRecordsGetDisciplinesUseCase = UPStudentRecordsGetDisciplinesUseCase()
): ViewModel() {

    private val _disciplinesUIState = getDisciplinesUseCase()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())
    val disciplinesUIState = _disciplinesUIState.asStateFlow()

    fun getDisciplines() = getDisciplinesUseCase()
        .collectToFlow(_disciplinesUIState, viewModelScope)

}