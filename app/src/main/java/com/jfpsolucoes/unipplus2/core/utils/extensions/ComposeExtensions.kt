package com.jfpsolucoes.unipplus2.core.utils.extensions

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jfpsolucoes.unipplus2.BuildConfig
import com.jfpsolucoes.unipplus2.R
import kotlinx.coroutines.launch

val activity: ComponentActivity
 @SuppressLint("ContextCastToActivity") @Composable
 get() = LocalContext.current as ComponentActivity

@Composable
fun ShowInterstitialAd(
    onAdDismissed: () -> Unit = {}
) {
    var hasShowed by rememberSaveable { mutableStateOf(false) }
    if (hasShowed) return

    val unitId = if (BuildConfig.DEBUG) R.string.admob_test_interstitial_id
    else R.string.admob_interstitial_id

    val activity = activity

    val request = AdRequest.Builder().build()
    InterstitialAd.load(
        activity,
        stringResource(unitId),
        request,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                onAdDismissed()
            }

            override fun onAdLoaded(ad: InterstitialAd) {
                super.onAdLoaded(ad)
                ad.show(activity)
                ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        onAdDismissed()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)
                        onAdDismissed()
                    }
                }

                hasShowed = true
            }
        }
    )
}