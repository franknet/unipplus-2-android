package com.jfpsolucoes.unipplus2.ui.components.web

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import com.jfpsolucoes.unipplus2.core.networking.UPWebViewClient
import com.jfpsolucoes.unipplus2.core.utils.extensions.ScreenOrientation
import com.jfpsolucoes.unipplus2.core.utils.extensions.requestScreenOrientation

const val TAG = "PortalWebView"

@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PortalWebView(
    modifier: Modifier = Modifier,
    url: String
) = Scaffold {

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.setSupportZoom(true)
                settings.displayZoomControls = true
                settings.builtInZoomControls = true
                webViewClient = UPWebViewClient()
                loadUrl(url)
            }
        }
    )
}