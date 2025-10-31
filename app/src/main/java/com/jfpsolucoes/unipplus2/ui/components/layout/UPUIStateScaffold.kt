package com.jfpsolucoes.unipplus2.ui.components.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jfpsolucoes.unipplus2.ui.UIState

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun <T> UPUIStateScaffold(
    modifier: Modifier = Modifier,
    state: UIState<T>,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    loadingContent: @Composable (PaddingValues) -> Unit,
    errorContent: @Composable (PaddingValues, Throwable) -> Unit,
    content: @Composable (PaddingValues, T) -> Unit
) = Scaffold(
    modifier = modifier,
    topBar = topBar,
    bottomBar = bottomBar,
    snackbarHost = snackbarHost,
    containerColor = Color.Transparent
) {  padding ->
    when (state) {
        is UIState.UIStateLoading -> loadingContent.invoke(padding)
        is UIState.UIStateError -> { state.error?.let { errorContent.invoke(padding, it) } }
        is UIState.UIStateSuccess -> { state.data?.let { content.invoke(padding, it) } }
        else -> {}
    }
}

@Composable
fun <T> UPUIStateView(
    state: UIState<T>,
    loadingContent: @Composable () -> Unit,
    errorContent: @Composable (Throwable) -> Unit,
    content: @Composable (T) -> Unit
) {
    when (state) {
        is UIState.UIStateLoading -> loadingContent.invoke()
        is UIState.UIStateError -> { state.error?.let { errorContent.invoke(it) } }
        is UIState.UIStateSuccess -> { state.data?.let { content.invoke(it) } }
        else -> {}
    }
}