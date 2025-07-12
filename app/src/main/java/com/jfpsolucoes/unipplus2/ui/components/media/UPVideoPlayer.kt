package com.jfpsolucoes.unipplus2.ui.components.media

import android.net.Uri
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun UPVideoPlayer(modifier: Modifier = Modifier) {
    val videoUri = Uri.parse("https://dntt371fbvvo.cloudfront.net/out/v1/3a9722dc6b3d4b42a4469b0ea6550a34/4493932525bb45cc9eeb7ae94162ae38/631cdd7721c5451d99a1a3183cbb8124/index.m3u8")

    AndroidView(
        modifier = modifier,
        factory = {
        VideoView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setMediaController(MediaController(it))
            setVideoURI(videoUri)
            start()
        }
    })
}