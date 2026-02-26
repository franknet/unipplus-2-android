package com.jfpsolucoes.unipplus2.core.utils.extensions

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
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