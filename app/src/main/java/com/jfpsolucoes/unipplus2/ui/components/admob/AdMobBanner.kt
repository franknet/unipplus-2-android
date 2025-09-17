package com.jfpsolucoes.unipplus2.ui.components.admob

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jfpsolucoes.unipplus2.R

@Composable
fun AdMobBanner(modifier: Modifier = Modifier) {
    val adUnitId = stringResource(R.string.admob_banner_id)
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).also { view ->
                view.adUnitId = adUnitId
                view.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, 360))
                view.loadAd(AdRequest.Builder().build())
            }
        }
    )
}