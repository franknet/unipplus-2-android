package com.jfpsolucoes.unipplus2.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.caverock.androidsvg.SVG
import com.jfpsolucoes.unipplus2.R

const val ICON_GLOBE = "ic_globe"
const val ICON_HEADSET = "ic_headset"
const val ICON_SETTINGS = "ic_settings"

private val outlinedIconId = mapOf(
    ICON_GLOBE to R.drawable.ic_outline_globe_24,
    ICON_HEADSET to R.drawable.ic_outline_headset_mic_24,
    ICON_SETTINGS to R.drawable.ic_outline_services_24,
)

object UPIcons
{
    object Outlined
    {
        @Composable
        fun of(name: String): Painter = painterResource(id = outlinedIconId[name] ?: R.drawable.ic_outline_globe_24 )
    }

}

