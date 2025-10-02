package com.jfpsolucoes.unipplus2.ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import java.nio.ByteBuffer

@Composable
fun Image(
    modifier: Modifier = Modifier,
    svgString: String,
    contentDescription: String? = null,
    color: Color = Color.Black
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {
        add(SvgDecoder.Factory())
    }.build()

    val model = ImageRequest.Builder(LocalContext.current)
        .data(ByteBuffer.wrap(svgString.toByteArray()))
        .decoderFactory(SvgDecoder.Factory())
        .build()

    AsyncImage(
        model = model,
        imageLoader = imageLoader,
        contentDescription = contentDescription, // Provide a meaningful description for accessibility
        modifier = modifier, // Set desired size
        colorFilter = ColorFilter.tint(color)
    )
}