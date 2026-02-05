package com.jfpsolucoes.unipplus2.core.networking

import android.graphics.Bitmap
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri

private const val TAG = "UPWebViewClient"

interface WebViewClientListener {
    fun onLoadingStarted()
    fun onLoadingFinished()
}

class UPWebViewClient(
    val listener: WebViewClientListener? = null
): WebViewClient() {
    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
        url?.toUri()?.let {
            // Store all cookies from web page
            if (it.host == "www.unipplus.com.br") {
                val cookie = CookieManager.getInstance().getCookie(url)
                Log.i(TAG, "onLoadResource: $cookie")
            }
        }
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        listener?.onLoadingStarted()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        listener?.onLoadingFinished()
    }

    // Enabled all urls redirections
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return false
    }
}