package com.jfpsolucoes.unipplus2.ui.components.web

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import com.jfpsolucoes.unipplus2.core.networking.UPWebViewClient
import com.jfpsolucoes.unipplus2.core.utils.extensions.mutableState

const val TAG = "PortalWebView"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PortalWebView(
    modifier: Modifier = Modifier,
    webSettings: PortalWebViewSettings,
    onClickBack: (() -> Unit)? = null
) {
    var webView: WebView? = null
    Scaffold(
        modifier = modifier.safeDrawingPadding(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    onClickBack?.let {
                        IconButton(onClick = it) {
                            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null)
                        }
                    }
                },
                title = {
                    Text(
                        webSettings.url,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { webView?.reload() }) {
                        Icon(imageVector = Icons.Outlined.Refresh, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        AndroidView(
            modifier = Modifier.padding(top = padding.calculateTopPadding()),
            factory = {
                WebView(it).apply {

                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.setSupportZoom(true)
                    settings.displayZoomControls = false
                    webViewClient = UPWebViewClient()
                }
            },
            update = {
                webView = it
                it.loadUrl(webSettings.url)
            }
        )
    }
}
