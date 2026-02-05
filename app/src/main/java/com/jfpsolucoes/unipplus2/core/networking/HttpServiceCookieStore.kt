package com.jfpsolucoes.unipplus2.core.networking

import android.util.Log
import java.net.CookieManager
import java.net.CookieStore
import java.net.HttpCookie
import java.net.URI

private const val TAG = "HttpServiceCookieStore"
private const val WEB_VIEW_TAG = "Webkit.CookieManager"

class HttpServiceCookieStore(
    private val cookieStore: CookieStore = CookieManager().cookieStore,
    private val androidCookieStore: android.webkit.CookieManager = android.webkit.CookieManager.getInstance().also {
        it.setAcceptCookie(true)
    }
): CookieStore {

    override fun add(uri: URI?, cookie: HttpCookie?) {
        Log.i(TAG, "add: $cookie")
        cookieStore.add(uri, cookie)
        androidCookieStore.add(uri, cookie)
    }

    override fun get(uri: URI?): MutableList<HttpCookie> {
        Log.i(TAG, "get: $uri")
        return cookieStore.get(uri)
    }

    override fun getCookies(): MutableList<HttpCookie> {
        Log.i(TAG, "getCookies")
        return cookieStore.cookies
    }

    override fun getURIs(): MutableList<URI> {
        Log.i(TAG, "getURIs")
        return cookieStore.urIs
    }

    override fun remove(uri: URI?, cookie: HttpCookie?): Boolean {
        Log.i(TAG, "remove: $cookie")
        return cookieStore.remove(uri, cookie)
    }

    override fun removeAll(): Boolean {
        Log.i(TAG, "removeAll")
        return cookieStore.removeAll()
    }
}

private fun android.webkit.CookieManager.add(uri: URI?, cookie: HttpCookie?) {
    cookie?.let {
        if (it.name == "ASP.NET_SessionId") {
            Log.i(WEB_VIEW_TAG, "add: $it")
            setCookie("https://sec2.unip.br", "$it")
        }
        if (it.name == "SESSION") {
            Log.i(WEB_VIEW_TAG, "add: $it")
            setCookie("https://gfa.unip.br/aluno.html", "$it; Path=/aluno; HttpOnly")
        }
        // Store cookie immediately
        flush()
    }
}