package com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.analytics.UPAnalyticsManager
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.models.UPCalendarData
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.models.UP_CALENDAR_FORMAT_FULL
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.usecases.UPGetCalendarUseCase
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.usecases.UPUpdateCalendarUseCase
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UPCalendarViewModel(
    val getCalendarUseCase: UPGetCalendarUseCase = UPGetCalendarUseCase(),
    val updateCalendarUseCase: UPUpdateCalendarUseCase = UPUpdateCalendarUseCase(),
) : ViewModel() {

    private val _calendarUIState = MutableSharedFlow<UIState<UPCalendarData>>()
    val calendarUIState = _calendarUIState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UIState.UIStateLoading()
        )

    fun fetch(format: String = UP_CALENDAR_FORMAT_FULL) = viewModelScope.launch {
        getCalendarUseCase(format).collect {
            _calendarUIState.emit(it)
        }
    }

    fun updateCalendar(path: String) = viewModelScope.launch {
        _calendarUIState.emit(UIState.UIStateLoading())
        updateCalendarUseCase(path).collect {
            _calendarUIState.emit(it)
        }
    }

    fun trackScreenView() {
        UPAnalyticsManager.trackScreenView("UPCalendarView")
    }
}
