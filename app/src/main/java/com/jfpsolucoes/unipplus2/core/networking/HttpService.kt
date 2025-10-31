package com.jfpsolucoes.unipplus2.core.networking

import android.content.Context
import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import okhttp3.Cache
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

object HttpService {
    private val mCookieManager = CookieManager(HttpServiceCookieStore(), CookiePolicy.ACCEPT_ALL)

    private val mCookiejar = JavaNetCookieJar(mCookieManager)

    private var mClient: OkHttpClient? = null

    private var mRetrofit: Retrofit? = null

    private const val CACHE_SIZE = (5 * 1024 * 1024).toLong()

    private const val CACHE_DIR_NAME = "unip_plus_2"

    fun initialize(context: Context) {
        if (mRetrofit != null) return

        val cacheDir = File(context.cacheDir, CACHE_DIR_NAME)
        val cache = Cache(cacheDir, CACHE_SIZE)

        mClient = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .cache(cache)
            .cookieJar(mCookiejar)
            .addInterceptor(HttpServiceRequestInterceptor())
            .addInterceptor(HttpServiceLoggingInterceptor)
            .build()

        mRetrofit = Retrofit.Builder()
            .baseUrl(UPApiEndpoints.BASE_URL)
            .client(mClient ?: throw IllegalStateException("Client not initialized"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(
        service: Class<T>,
    ): T = mRetrofit?.create(service) ?: throw IllegalStateException("Retrofit not initialized")
}