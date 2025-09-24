package com.jfpsolucoes.unipplus2.core.utils.extensions

val <E> List<E>?.value: List<E>
    get() = this ?: emptyList()