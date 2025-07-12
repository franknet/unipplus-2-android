package com.jfpsolucoes.unipplus2.modules.studentprofile.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPStudentProfile
import com.jfpsolucoes.unipplus2.modules.studentprofile.domain.GetStudentProfileUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UPStudentProfileViewModel(
    val getStudentProfileUseCase: GetStudentProfileUseCase = GetStudentProfileUseCase()
): ViewModel() {
    var studentProfileUIState by mutableStateOf<UIState<UPStudentProfile?>>(UIState.UIStateLoading())

    suspend fun loadData() = viewModelScope.launch {
        getStudentProfileUseCase()
            .flowOn(Dispatchers.IO)
            .collect(::onGetStudentProfile)
    }

    private fun onGetStudentProfile(state: UIState<UPStudentProfile?>) {
        studentProfileUIState = state
    }
}