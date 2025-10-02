package com.jfpsolucoes.unipplus2.core.database

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

const val SHARED_KEY_APP_INFO = "appInfo"

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
}