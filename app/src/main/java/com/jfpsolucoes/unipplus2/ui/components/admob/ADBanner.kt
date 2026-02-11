package com.jfpsolucoes.unipplus2.ui.components.admob

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jfpsolucoes.unipplus2.R

@Composable
fun ADBanner(
    modifier: Modifier = Modifier
) {
    if (LocalInspectionMode.current) return

    val adUnitId = stringResource(R.string.admob_banner_id)

    val adView = AdView(LocalContext.current).also { view ->
        view.adUnitId = adUnitId
        view.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(LocalContext.current, 360))
        view.loadAd(AdRequest.Builder().build())
    }

    AndroidView(modifier = modifier.wrapContentSize(), factory = { adView })

    LifecycleResumeEffect(adView) {
        adView.resume()
        onPauseOrDispose { adView.pause() }
    }
}