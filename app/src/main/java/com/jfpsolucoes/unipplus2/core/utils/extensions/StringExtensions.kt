package com.jfpsolucoes.unipplus2.core.utils.extensions

import android.content.Context
import io.noties.markwon.Markwon

val String?.value
    get() = this ?: ""

fun String?.toAnnotatedString(context: Context) = Markwon
    .create(context)
    .toMarkdown(this.value)

