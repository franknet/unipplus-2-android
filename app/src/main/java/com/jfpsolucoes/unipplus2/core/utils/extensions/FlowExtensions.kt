package com.jfpsolucoes.unipplus2.core.utils.extensions

import android.util.Log
import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

fun <T> Flow<T>.toUIStateFlow(): Flow<UIState<T>> {
    return this
        .map<T, UIState<T>> { UIState.UIStateSuccess(data = it) }
        .onStart { emit(UIState.UIStateLoading()) }
        .catch { emit(UIState.UIStateError(error = it)) }
}

fun <T> Flow<UIState<T>>.collectToFlow(flow: MutableStateFlow<UIState<T>>, scope: CoroutineScope) = scope.launch {
    collect { flow.emit(it) }
}

fun <T> Flow<UIState<T>>.debugPrint(tag: String): Flow<UIState<T>> {
    return this.map {
        if (BuildConfig.DEBUG) { Log.d("Flow Event", "$tag: $it") }
        it
    }.catch {
        if (BuildConfig.DEBUG) { Log.e("Flow Event", "$tag: $it") }
        it
    }
}