package com.jfpsolucoes.unipplus2.core.utils.extensions

import androidx.compose.material3.DrawerState

suspend fun DrawerState.closeIfOpened() {
    if (isOpen) { this.close() }
}