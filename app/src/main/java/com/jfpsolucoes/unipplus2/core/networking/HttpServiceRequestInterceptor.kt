package com.jfpsolucoes.unipplus2.core.networking

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val HEADER_X_UUID = "x-uuid"
private const val HEADER_X_USER_RG = "x-user-rg"
private const val HEADER_X_USER_ID = "x-user-id"
private const val HEADER_X_COURSE_TYPE = "x-course-type"
private const val HEADER_X_TOKEN = "x-token"
private const val HEADER_X_REFRESH_TOKEN = "x-refresh-token"

class HttpServiceRequestInterceptor: Interceptor {
    private var mHeadersStore = mutableMapOf<String, String?>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = buildRequestFrom(chain)
        val response = chain.proceed(newRequest)

        // Update stored headers store
        response.header(HEADER_X_UUID)?.let { mHeadersStore[HEADER_X_UUID] = it }
        response.header(HEADER_X_USER_RG)?.let { mHeadersStore[HEADER_X_USER_RG] = it }
        response.header(HEADER_X_USER_ID)?.let { mHeadersStore[HEADER_X_USER_ID] = it }
        response.header(HEADER_X_COURSE_TYPE)?.let { mHeadersStore[HEADER_X_COURSE_TYPE] = it }
        response.header(HEADER_X_TOKEN)?.let { mHeadersStore[HEADER_X_TOKEN] = it }
        response.header(HEADER_X_REFRESH_TOKEN)?.let { mHeadersStore[HEADER_X_REFRESH_TOKEN] = it }

        return response
    }

    private fun buildRequestFrom(chain: Interceptor.Chain): Request {
        val requestBuilder =  chain.request().newBuilder()

        // Set stored headers
        mHeadersStore[HEADER_X_UUID]?.let { requestBuilder.addHeader(HEADER_X_UUID, it) }
        mHeadersStore[HEADER_X_USER_RG]?.let { requestBuilder.addHeader(HEADER_X_USER_RG, it) }
        mHeadersStore[HEADER_X_USER_ID]?.let { requestBuilder.addHeader(HEADER_X_USER_ID, it) }
        mHeadersStore[HEADER_X_COURSE_TYPE]?.let { requestBuilder.addHeader(HEADER_X_COURSE_TYPE, it) }
        mHeadersStore[HEADER_X_TOKEN]?.let { requestBuilder.addHeader(HEADER_X_TOKEN, it) }
        mHeadersStore[HEADER_X_REFRESH_TOKEN]?.let { requestBuilder.addHeader(HEADER_X_REFRESH_TOKEN, it) }
        return requestBuilder.build()
    }


}