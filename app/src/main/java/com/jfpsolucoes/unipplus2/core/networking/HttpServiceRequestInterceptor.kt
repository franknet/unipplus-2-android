package com.jfpsolucoes.unipplus2.core.networking

import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.core.database.dao.UPNetworkDao
import com.jfpsolucoes.unipplus2.core.database.entities.UPNetworkEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val HEADER_X_UUID = "x-uuid"
private const val HEADER_X_USER_RG = "x-user-rg"
private const val HEADER_X_USER_ID = "x-user-id"
private const val HEADER_X_COURSE_TYPE = "x-course-type"
private const val HEADER_X_TOKEN = "x-token"
private const val HEADER_X_REFRESH_TOKEN = "x-refresh-token"
private const val HEADER_X_APP_VERSION = "x-app-version"
private const val HEADER_X_APP_PLATFORM = "x-app-platform"

class HttpServiceRequestInterceptor(
    private val networkDao: UPNetworkDao,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : Interceptor {
    private var mHeadersStore = mutableMapOf<String, String?>()


    init {
        loadTokensFromDatabase()
    }

    private fun loadTokensFromDatabase() = scope.launch {
        networkDao.get().filterNotNull().collect {
            mHeadersStore[HEADER_X_UUID] = it.uuid
            mHeadersStore[HEADER_X_USER_ID] = it.userId
            mHeadersStore[HEADER_X_USER_RG] = it.userRg
            mHeadersStore[HEADER_X_COURSE_TYPE] = it.courseType
            mHeadersStore[HEADER_X_TOKEN] = it.accessToken
            mHeadersStore[HEADER_X_REFRESH_TOKEN] = it.refreshToken
        }
    }

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

        // Save tokens to database if updated
        saveTokensToDatabase()

        return response
    }

    private fun saveTokensToDatabase() = scope.launch {
        val updated = UPNetworkEntity(
            uuid = mHeadersStore[HEADER_X_UUID].orEmpty(),
            userId = mHeadersStore[HEADER_X_USER_ID].orEmpty(),
            userRg = mHeadersStore[HEADER_X_USER_RG].orEmpty(),
            courseType = mHeadersStore[HEADER_X_COURSE_TYPE].orEmpty(),
            accessToken = mHeadersStore[HEADER_X_TOKEN].orEmpty(),
            refreshToken = mHeadersStore[HEADER_X_REFRESH_TOKEN].orEmpty()
        )
        networkDao.insert(updated)
    }

    private fun buildRequestFrom(chain: Interceptor.Chain): Request {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(HEADER_X_APP_VERSION, BuildConfig.VERSION_NAME)
        requestBuilder.addHeader(HEADER_X_APP_PLATFORM, "android")

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