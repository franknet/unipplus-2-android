package com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.usecases

import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.data.UPCalendarRepository
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.models.UP_CALENDAR_FORMAT_FULL
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.repository.UPCalendarRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UPGetCalendarUseCase(
    val repository: UPCalendarRepository = UPCalendarRepositoryImpl(),
    val context: CoroutineContext = Dispatchers.IO
) {
    operator fun invoke(format: String = UP_CALENDAR_FORMAT_FULL) = flow {
        val data = withContext(context) {
            repository.getCalendar(format)
        }
        emit(data)
    }.toUIStateFlow()
}