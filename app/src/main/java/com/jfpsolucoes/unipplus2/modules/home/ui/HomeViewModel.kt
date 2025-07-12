package com.jfpsolucoes.unipplus2.modules.home.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class HomeViewModel: ViewModel() {

    var counter = 0

    val count = flow {
        while (true) {
            delay(1000L)
            counter++
            emit(counter)
        }
    }

    override fun onCleared() {
        println("HomeView Cleared")
    }
}