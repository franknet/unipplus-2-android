package com.jfpsolucoes.unipplus2.core.prefs

/**
 * Annotation to mark a field or property that should be populated with a JSON String
 * fetched from Android SharedPreferences.
 *
 * Usage:
 *
 * class ExampleHolder {
 *     @field:UPPrefJson(key = "user_profile_json")
 *     var userProfileJson: String = ""
 * }
 *
 * // Later, inject values from SharedPreferences
 * // PrefJsonInjector.inject(context, exampleHolder)
 *
 * By default, this reads from a SharedPreferences file named "unipplus_prefs".
 * You may override it by specifying [prefsName].
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
annotation class UPPrefJson(
    val key: String,
    val default: String = "",
    val prefsName: String = "unipplus_prefs"
)
