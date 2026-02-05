package com.jfpsolucoes.unipplus2.ui.components.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView


const val TAG = "PortalWebView"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PortalWebView(
    modifier: Modifier = Modifier,
    webSettings: PortalWebViewSettings,
    cookieManager: CookieManager = CookieManager.getInstance(),
    onClickBack: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }

    val webView = WebView(context).apply {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        settings.setSupportMultipleWindows(true)
        // Client
        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                isLoading = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                isLoading = false
            }
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return true
            }
        }
        webChromeClient = WebChromeClient()

        // Sync cookies
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(this, true)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            PortalWebViewTopBar(
                url = webSettings.url,
                onClickBack = onClickBack,
                onClickRefreshPage = webView::reload
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        if (isLoading) {
            UPLoadingView()
        } else {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    ),
                factory = { webView },
                update = { webView.loadUrl(webSettings.url) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PortalWebViewTopBar(
    url: String,
    onClickBack: (() -> Unit)? = null,
    onClickRefreshPage: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            onClickBack?.let {
                IconButton(onClick = it) {
                    Icon(painter = painterResource(R.drawable.ic_outline_arrow_back_24), contentDescription = null)
                }
            }
        },
        title = {
            Text(
                url,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = onClickRefreshPage) {
                Icon(painter = painterResource(R.drawable.ic_outline_refresh_24), contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        )
    )
}
