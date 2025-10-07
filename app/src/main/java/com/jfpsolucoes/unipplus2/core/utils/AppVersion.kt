package com.jfpsolucoes.unipplus2.core.utils

import android.content.Context
import android.content.pm.PackageManager

fun getAppVersion(context: Context): Pair<Int, String> {
    try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return Pair(packageInfo.versionCode, packageInfo.versionName ?: "Unknown")
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return Pair(-1, "Unknown") // Handle error case
}

