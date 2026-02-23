package com.jfpsolucoes.unipplus2.core.file

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object UPFileProviderManager {
    fun share(pdfFile: File, context: Context) {
        val uri: Uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".fileprovider",
            pdfFile
        )
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }
}
