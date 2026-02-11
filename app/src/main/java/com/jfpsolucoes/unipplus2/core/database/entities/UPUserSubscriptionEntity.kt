package com.jfpsolucoes.unipplus2.core.database.entities

data class UPUserSubscriptionEntity(
    val token: String? = null,
    val productId: String? = null,
    val expiresAt: String? = null,
    val active: Boolean = false
)