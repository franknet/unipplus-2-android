package com.jfpsolucoes.unipplus2.core.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

val <T> T.mutableState: MutableState<T>
    get() = mutableStateOf(this)

val <T> T.mutableStateFlow: MutableStateFlow<T>
    get() = MutableStateFlow(this)

val <T> T.stateFlow: StateFlow<T>
    get() = MutableStateFlow(this).asStateFlow()

fun <T> T.state(value: T?): MutableState<T?> = mutableStateOf(value)

val <T> T.rememberState: MutableState<T>
    @Composable get() = remember { mutableStateOf(this) }

@Composable
fun <T> T.rememberState(value: T?): MutableState<T?> = remember { mutableStateOf(value) }

val <T> T.saveableMutableState: MutableState<T>
    @Composable get() = rememberSaveable { mutableStateOf(this) }

@Composable
fun <T> T.saveableState(value: T?): MutableState<T?> = rememberSaveable { mutableStateOf(value) }

fun <T> value(value: T?): T? = value