package com.jfpsolucoes.unipplus2.core.networking

import com.jfpsolucoes.unipplus2.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

val HttpServiceLoggingInterceptor = HttpLoggingInterceptor(logger = {
    if (BuildConfig.DEBUG) { println(it) }
}).apply {
    level = HttpLoggingInterceptor.Level.BODY
}