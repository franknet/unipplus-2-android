package com.jfpsolucoes.unipplus2.modules.subscriptions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfpsolucoes.unipplus2.core.store.payment.SubscriptionManagerInstance
import com.jfpsolucoes.unipplus2.core.store.payment.UPSubscriptionManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.collectAsMutableStateFlow
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.flow.asStateFlow

class UPSubscriptionViewModel(
    val manager: UPSubscriptionManager = SubscriptionManagerInstance
): ViewModel() {
    private val _subscriptionsState = manager.subscriptions
        .toUIStateFlow()
        .collectAsMutableStateFlow(viewModelScope, UIState.UIStateNone())

    val subscriptionsState = _subscriptionsState.asStateFlow()

}