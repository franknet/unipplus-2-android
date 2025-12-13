package com.jfpsolucoes.unipplus2.ui

sealed class UIState<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class UIStateNone<T> : UIState<T>()
    class UIStateLoading<T> : UIState<T>()
    class UIStateError<T>(error: Throwable) : UIState<T>(error = error)
    class UIStateSuccess<T>(data: T?) : UIState<T>(data = data)

    val loading: Boolean
        get() = this is UIStateLoading

    val success: Boolean
        get() = this is UIStateSuccess

    val failure: Boolean
        get() = this is UIStateError
}
