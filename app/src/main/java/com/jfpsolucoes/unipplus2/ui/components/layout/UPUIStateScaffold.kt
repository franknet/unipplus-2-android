
package com.jfpsolucoes.unipplus2.ui.components.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.ui.UIState

@ExperimentalMaterial3Api
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun <T> UPUIStateScaffold(
    modifier: Modifier = Modifier,
    state: UIState<T>,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    loadingContent: @Composable (PaddingValues) -> Unit,
    errorContent: @Composable (PaddingValues, Throwable) -> Unit,
    content: @Composable (PaddingValues, T) -> Unit
) = Scaffold(
    modifier = modifier,
    topBar = topBar,
    bottomBar = bottomBar,
    snackbarHost = snackbarHost,
    floatingActionButton = floatingActionButton,
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