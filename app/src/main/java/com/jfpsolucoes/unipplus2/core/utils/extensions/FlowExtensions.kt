package com.jfpsolucoes.unipplus2.core.utils.extensions

import android.util.Log
import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

fun <T> Flow<T>.toUIStateFlow(): Flow<UIState<T>> {
    return this
        .map<T, UIState<T>> { UIState.UIStateSuccess(data = it) }
        .onStart { emit(UIState.UIStateLoading()) }
        .catch { emit(UIState.UIStateError(error = it)) }
}

fun <T> Flow<T>.collectToFlow(flow: MutableStateFlow<T>, scope: CoroutineScope) = scope.launch {
    collect { flow.emit(it) }
}

suspend fun <T> Flow<T>.collectToFlow(flow: MutableStateFlow<T>) {
    collect { flow.emit(it) }
}

fun <T> Flow<T>.collectAsMutableStateFlow(scope: CoroutineScope, initialValue: T): MutableStateFlow<T> {
    val mutableFlow = MutableStateFlow(value = initialValue)
    collectToFlow(mutableFlow, scope)
    return mutableFlow
}

fun <T> Flow<T>.debugPrint(tag: String): Flow<T> {
    if (!BuildConfig.DEBUG) return this

    return this.onEach {
        Log.i(tag, "$it")
    }
}