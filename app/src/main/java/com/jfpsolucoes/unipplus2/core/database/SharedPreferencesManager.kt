package com.jfpsolucoes.unipplus2.core.database

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

const val SHARED_KEY_APP_INFO = "appInfo"
const val SHARED_KEY_APP_SHARE_ALERT_COUNT = "shareAlertCount"

object SharedPreferencesManager {

    var editor: SharedPreferences? = null

    fun initialize(context: Context) {
        if (editor != null) { return }
        editor = context.getSharedPreferences("SharedPreferencesManager", Context.MODE_PRIVATE)
    }

    fun saveObject(key: String, obj: Any?) {
        val jsonStr = Gson().toJson(obj)
        editor?.edit { putString(key, jsonStr) }
    }

    inline fun <reified T : Any> getObject(key: String): T? {
        val jsonStr = editor?.getString(key, null)
        return Gson().fromJson(jsonStr, T::class.java)
    }

    fun setInt(key: String, value: Int) {
        editor?.edit { putInt(key, value) }
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return editor?.getInt(key, defaultValue) ?: defaultValue
    }

}