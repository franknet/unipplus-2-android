package com.jfpsolucoes.unipplus2.core.networking

import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri

private const val TAG = "UPWebViewClient"

class UPWebViewClient: WebViewClient() {
    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
        url?.toUri()?.let {
            if (it.host == "www.unipplus.com.br") {
                val cookie = CookieManager.getInstance().getCookie(url)
                Log.i(TAG, "onLoadResource: $cookie")
            }
        }

    }
}