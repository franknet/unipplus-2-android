package com.jfpsolucoes.unipplus2.core.utils.extensions

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jfpsolucoes.unipplus2.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

enum class ScreenOrientation(val value: Int) {
    PORTRAIT(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT),
    LANDSCAPE(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE),
    UNSPECIFIED(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
}

fun Activity.requestScreenOrientation(
    orientation: ScreenOrientation = ScreenOrientation.UNSPECIFIED
) {
    requestedOrientation = orientation.value
}

val ComponentActivity.windowInfoTracker: WindowInfoTracker
    get() = WindowInfoTracker.getOrCreate(this)

val ComponentActivity.windowLayoutInfo: Flow<WindowLayoutInfo>
    get() = windowInfoTracker.windowLayoutInfo(this)

fun ComponentActivity.getFoldingFeatures(callback: (List<FoldingFeature>?) -> Unit) {
    lifecycleScope.launch(Dispatchers.Main) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            windowLayoutInfo.collect {
                if (it.displayFeatures.isEmpty()) {
                    callback(null)
                } else {
                    val foldingFeatures = it.displayFeatures.map { feature -> feature as FoldingFeature }
                    callback(foldingFeatures)
                }
            }
        }
    }
}

fun Activity.showInterstitialAd(
    enabled: Boolean,
    onLoading: ((Boolean) -> Unit)? = null,
    onError: ((error: String?) -> Unit)? = null,
    onSuccess: (() -> Unit)? = null,
) {
    if (!enabled) {
        onSuccess?.invoke()
        return
    }
    val request = AdRequest.Builder().build()
    onLoading?.invoke(true)
    InterstitialAd.load(
        this,
        resources.getString(R.string.admob_interstitial_id),
        request,
        object : InterstitialAdLoadCallback() {


            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                onLoading?.invoke(false)
                onError?.invoke(p0.message)
                Log.e("InterstitialAd", p0.message)
            }
            override fun onAdLoaded(ad: InterstitialAd) {
                super.onAdLoaded(ad)
                onLoading?.invoke(false)
                ad.show(this@showInterstitialAd)
                ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        onSuccess?.invoke()
                    }
                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)
                        onError?.invoke(p0.message)
                        Log.e("InterstitialAd", p0.message)
                    }
                }
            }
        }
    )
}