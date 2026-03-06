package com.jfpsolucoes.unipplus2.ui.components.admob

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.utils.extensions.isHeightMediumLowerBound
import com.jfpsolucoes.unipplus2.core.utils.extensions.isWidthMediumLowerBound

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ADBanner(
    modifier: Modifier = Modifier,
    adUnitId: String = stringResource(R.string.admob_banner_id)
) {
    if (LocalInspectionMode.current) return
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val windowSize = currentWindowAdaptiveInfo(true).windowSizeClass
    val adSize = remember(windowSize, configuration.screenWidthDp) {
        if (!windowSize.isWidthMediumLowerBound || !windowSize.isHeightMediumLowerBound) {
            AdSize.getInlineAdaptiveBannerAdSize(configuration.screenWidthDp, 60)
        } else {
            AdSize.getLargeAnchoredAdaptiveBannerAdSize(context, configuration.screenWidthDp)
        }
    }

    val adView = remember(adSize) {
        AdView(context).also { view ->
            view.adUnitId = adUnitId
            view.setAdSize(adSize)
            view.adListener = object : AdListener() {
                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.i("ADBanner", "onAdImpression")
                }
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.i("ADBanner", "onAdLoaded")
                }
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.e("ADBanner", "LoadAdError ${p0.code}: ${p0.message}")
                }
            }
        }
    }

    LaunchedEffect(adView) {
        adView.loadAd(AdRequest.Builder().build())
    }

    AndroidView(modifier = modifier.fillMaxWidth(), factory = { adView })

    LifecycleResumeEffect(adView) {
        adView.resume()
        onPauseOrDispose {
            adView.pause()
        }
    }
}