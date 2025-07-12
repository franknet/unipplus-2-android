package com.jfpsolucoes.unipplus2.core.networking

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpServiceRequestInterceptor: Interceptor {
    private var xUserRg: String? = null
    private var xUserId: String? = null
    private var token: String? = null
    private var refreshToken: String? = null
    private var authorization: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = buildRequestFrom(chain)
        val response = chain.proceed(newRequest)
        response.header("x-user-rg")?.let { xUserRg = it }
        response.header("x-user-id")?.let { xUserId = it }
        response.header("token")?.let { token = it }
        response.header("refresh-token")?.let { refreshToken = it }
        response.header("authorization")?.let { authorization = it }
        return response
    }

    private fun buildRequestFrom(chain: Interceptor.Chain): Request {
        val requestBuilder =  chain.request().newBuilder()
        xUserRg?.let { requestBuilder.addHeader("x-user-rg", it) }
        xUserId?.let { requestBuilder.addHeader("x-user-id", it) }
        token?.let { requestBuilder.addHeader("token", it) }
        refreshToken?.let { requestBuilder.addHeader("refresh-token", it) }
        authorization?.let { requestBuilder.addHeader("authorization", it) }
        return requestBuilder.build()
    }
}