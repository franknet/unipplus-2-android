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

fun <T> Flow<T>.asMutableStateFlow(scope: CoroutineScope, initialValue: T): MutableStateFlow<T> {
    val mStateFlow = MutableStateFlow(initialValue)
    this.collectToFlow(mStateFlow, scope)
    return mStateFlow
}

fun <T> Flow<T>.asSharedFlow(scope: CoroutineScope): SharedFlow<T> {
    return this.shareIn(
        scope = scope,
        started = SharingStarted.Eagerly
    )
}

fun <T> Flow<T>.collectToFlow(flow: MutableStateFlow<T>, scope: CoroutineScope) = scope.launch {
    collect { flow.emit(it) }
}

fun <T> Flow<T>.bindTo(flow: MutableStateFlow<T>): Flow<T> {
    return onEach { flow.value = it }
}

inline fun <T, R> Flow<T>.bindTo(flow: MutableStateFlow<R>, crossinline transform: suspend (T) -> R): Flow<T> {
    map(transform).onEach { flow.value = it }
    return this
}

fun <T> Flow<T>.debugPrint(tag: String): Flow<T> {
    if (!BuildConfig.DEBUG) return this

    return this.onEach {
        Log.i(tag, "$it")
    }
}