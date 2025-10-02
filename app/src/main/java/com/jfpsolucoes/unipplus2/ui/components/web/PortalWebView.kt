package com.jfpsolucoes.unipplus2.ui.components.web

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.networking.UPWebViewClient
import com.jfpsolucoes.unipplus2.core.networking.WebViewClientListener
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableState
import com.jfpsolucoes.unipplus2.ui.components.admob.ADBanner
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView

const val TAG = "PortalWebView"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PortalWebView(
    modifier: Modifier = Modifier,
    webSettings: PortalWebViewSettings,
    onClickBack: (() -> Unit)? = null
) {
    var isLoading by false.mutableState

    val webView = WebView(LocalContext.current).apply {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.setSupportZoom(true)
        settings.displayZoomControls = false
        webViewClient = UPWebViewClient(object : WebViewClientListener {
            override fun onLoadingStarted() { isLoading = true }
            override fun onLoadingFinished() { isLoading = false }
        })
        loadUrl(webSettings.url)
    }

    Scaffold(
        modifier = modifier.safeDrawingPadding(),
        topBar = {
            PortalWebViewTopBar(
                url = webSettings.url,
                onClickBack = onClickBack,
                onClickRefreshPage = webView::reload
            )
        },
        bottomBar = { ADBanner(Modifier.fillMaxWidth()) }
    ) { padding ->
        if (isLoading) {
            UPLoadingView()
        } else {
            AndroidView(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                factory = { webView }
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
        }
    )
}
