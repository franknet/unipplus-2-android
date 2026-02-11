package com.jfpsolucoes.unipplus2.core.file

import android.content.Context
import java.io.File

object UPFileDirectoryManager {
    lateinit var cacheDirectory: File

    fun initialize(context: Context) {
        cacheDirectory = context.cacheDir
    }

}