package com.jfpsolucoes.unipplus2.core.prefs

import android.content.Context

/**
 * Helper to read JSON strings from SharedPreferences using [UPPrefJson] annotation.
 *
 * You can either:
 * 1) Populate all annotated String fields of an object:
 *    PrefJsonInjector.inject(context, targetObject)
 *
 * 2) Read a single value directly:
 *    val json = PrefJsonInjector.get(context, key = "some_key")
 */
object PrefJsonInjector {

    private const val DEFAULT_PREFS = "unipplus_prefs"

    /**
     * Populate all fields of [target] annotated with [UPPrefJson]. Only String fields are supported.
     */
    fun inject(context: Context, target: Any) {
        val cls = target.javaClass
        val fields = cls.declaredFields
        for (field in fields) {
            val ann = field.getAnnotation(UPPrefJson::class.java) ?: continue
            if (field.type != String::class.java) continue

            val prefsName = ann.prefsName.ifBlank { DEFAULT_PREFS }
            val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            val value = prefs.getString(ann.key, ann.default) ?: ann.default

            field.isAccessible = true
            field.set(target, value)
        }
    }

    /**
     * Reads a JSON string value directly from SharedPreferences.
     */
    fun get(
        context: Context,
        key: String,
        default: String = "",
        prefsName: String = DEFAULT_PREFS
    ): String {
        val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        return prefs.getString(key, default) ?: default
    }
}
