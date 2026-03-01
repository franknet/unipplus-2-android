package com.jfpsolucoes.unipplus2.core.store

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewManagerFactory
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigKeys
import com.jfpsolucoes.unipplus2.core.remoteconfig.RemoteConfigManager

object UPAppStoreManager {
    fun checkUpdate(context: Activity) {
        val manager = AppUpdateManagerFactory.create(context)
        val appInfoTask = manager.appUpdateInfo
        appInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                manager.startUpdateFlow(
                    appUpdateInfo,
                    context,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            }
        }
    }

    fun requestReview(context: AppCompatActivity) {
        val manager = ReviewManagerFactory.create(context)
        val request = manager.requestReviewFlow()
        request.addOnSuccessListener { reviewInfo ->
            reviewInfo.describeContents()
            manager.launchReviewFlow(context, reviewInfo)
        }
    }

    fun share(context: AppCompatActivity) {
        val intent = Intent().apply {
            type = "text/plain"
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, context.resources.getString(R.string.common_share_text))
            putExtra(Intent.EXTRA_TEXT, RemoteConfigManager.getString(RemoteConfigKeys.APP_SHARE_TEXT))
        }
        context.startActivity(Intent.createChooser(intent, null))
    }
}