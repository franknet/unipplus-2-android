package com.jfpsolucoes.unipplus2.core.networking

import okhttp3.Interceptor
import okhttp3.Response

class HttpServiceAppCheckInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        return chain.proceed(request.build())
    }
}