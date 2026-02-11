package com.jfpsolucoes.unipplus2.ui.components.pdf

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import java.io.File
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.net.toUri
import com.jfpsolucoes.unipplus2.ui.components.loading.UPLoadingView

@Composable
fun UPPdfView(
    modifier: Modifier = Modifier,
    pdfFile: File,
    pageIndex: Int = 0,
    scale: Float = 1f
) {
    val context = LocalContext.current

    var bitmap by remember(pdfFile, pageIndex, scale) { mutableStateOf<android.graphics.Bitmap?>(null) }
    var isLoading by remember(pdfFile, pageIndex, scale) { mutableStateOf(true) }
    var error by remember(pdfFile, pageIndex, scale) { mutableStateOf<String?>(null) }

    LaunchedEffect(pdfFile, pageIndex, scale) {
        isLoading = true
        error = null
        bitmap = try {
            withContext(Dispatchers.IO) {
                val uri = pdfFile.toUri()
                UPPdfRender.renderPdfToBitmap(context, uri, pageIndex, scale = scale)
            }
        } catch (t: Throwable) {
            error = t.message ?: "Erro ao renderizar PDF"
            null
        }
        isLoading = false
    }

    when {
        isLoading -> UPLoadingView(modifier = modifier)
        bitmap != null -> Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = "PDF page",
            modifier = modifier,
            contentScale = ContentScale.Fit
        )
        else -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error ?: "Não foi possível abrir o PDF", textAlign = TextAlign.Center)
        }
    }
}