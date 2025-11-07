package com.jfpsolucoes.unipplus2.core.store

import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewManagerFactory

object UPAppStoreManager {
    fun checkUpdate(context: AppCompatActivity) {
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
}