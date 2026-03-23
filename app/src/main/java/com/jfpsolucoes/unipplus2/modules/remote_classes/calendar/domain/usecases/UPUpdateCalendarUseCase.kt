package com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.usecases

import androidx.core.net.toUri
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.data.UPCalendarRepository
import com.jfpsolucoes.unipplus2.modules.remote_classes.calendar.domain.repository.UPCalendarRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UPUpdateCalendarUseCase(
    val repository: UPCalendarRepository = UPCalendarRepositoryImpl(),
    val context: CoroutineContext = Dispatchers.IO
) {
    operator fun invoke(path: String) = flow {
        val uri = path.toUri()
        val query = uri.queryParameterNames
            .associateWith { uri.getQueryParameter(it) }
            .filter { it.value != null }
        val data = withContext(context) {
            repository.updateCalendar(path, query)
        }
        emit(data)
    }.toUIStateFlow()
}