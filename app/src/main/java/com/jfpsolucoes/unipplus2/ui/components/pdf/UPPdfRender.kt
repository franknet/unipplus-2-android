package com.jfpsolucoes.unipplus2.ui.components.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UPPdfRender {
    private const val TAG = "UPPdfRender"

    /**
     * Render a page of a PDF located at [uri] into a Bitmap.
     *
     * @param context Context used to open the URI.
     * @param uri Uri pointing to the PDF file.
     * @param pageIndex Which page to render (0-based). Default is 0.
     * @param targetWidth Optional desired bitmap width. If null, the page width * scale is used.
     * @param targetHeight Optional desired bitmap height. If null, the page height * scale is used.
     * @param scale Additional scale factor applied to the page size when targetWidth/Height are not provided.
     * @return A Bitmap with the rendered page, or null on failure or unsupported API level.
     */
    fun renderPdfToBitmap(
        context: Context,
        uri: Uri,
        pageIndex: Int = 0,
        targetWidth: Int? = null,
        targetHeight: Int? = null,
        scale: Float = 1f
    ): Bitmap? {
        // PdfRenderer requires API 21+. Project minSdk is >= 24 so this check is unnecessary.

        var pfd: ParcelFileDescriptor? = null
        var renderer: PdfRenderer? = null

        try {
            pfd = context.contentResolver.openFileDescriptor(uri, "r")
            if (pfd == null) {
                Log.e(TAG, "Unable to open ParcelFileDescriptor for uri: $uri")
                return null
            }

            renderer = PdfRenderer(pfd)

            if (pageIndex < 0 || pageIndex >= renderer.pageCount) {
                Log.e(TAG, "Invalid pageIndex $pageIndex for PDF with ${renderer.pageCount} pages")
                return null
            }

            val page = renderer.openPage(pageIndex)
            page.use { pdfPage ->
                val pageWidth = pdfPage.width
                val pageHeight = pdfPage.height

                val bmpWidth = targetWidth ?: (pageWidth * scale).toInt().coerceAtLeast(1)
                val bmpHeight = targetHeight ?: (pageHeight * scale).toInt().coerceAtLeast(1)

                val bitmap = createBitmap(bmpWidth, bmpHeight)

                // Compute scaling matrix to map PDF page size -> bitmap size
                val matrix = Matrix()
                val sx = bmpWidth.toFloat() / pageWidth.toFloat()
                val sy = bmpHeight.toFloat() / pageHeight.toFloat()
                matrix.setScale(sx, sy)

                pdfPage.render(bitmap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                return bitmap
            }
        } catch (e: Exception) {
            return null
        } finally {
            try {
                renderer?.close()
            } catch (_: Exception) { }
            try {
                pfd?.close()
            } catch (_: Exception) { }
        }
    }

}