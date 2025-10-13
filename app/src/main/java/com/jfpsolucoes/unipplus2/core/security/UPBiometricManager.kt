package com.jfpsolucoes.unipplus2.core.security

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.jfpsolucoes.unipplus2.R

interface UPBiometricManager {
    val isBiometricAvailable: Boolean
    fun initialize(context: AppCompatActivity)
     fun authenticate(
        activity: AppCompatActivity,
        title: String = activity.getString(R.string.biometric_title_text),
        subtitle: String = activity.getString(R.string.biometric_subtitle_text),
        description: String = activity.getString(R.string.biometric_description_text),
        negativeButtonText: String = activity.getString(R.string.common_cancel_text),
        onSuccess: () -> Unit,
        onError: (errorCode: Int, errorMessage: String) -> Unit,
        onFailed: () -> Unit
    )
}
object UPBiometricManagerImpl: UPBiometricManager {
    private lateinit var biometricManager: BiometricManager
    override fun initialize(context: AppCompatActivity) {
        biometricManager = BiometricManager.from(context)
    }

    override val isBiometricAvailable: Boolean
        get() = when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }

    override fun authenticate(
        activity: AppCompatActivity,
        title: String,
        subtitle: String,
        description: String,
        negativeButtonText: String,
        onSuccess: () -> Unit,
        onError: (errorCode: Int, errorMessage: String) -> Unit,
        onFailed: () -> Unit
    ) {
        val biometricPrompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errorCode, errString.toString())
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onFailed()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .apply {
                if (subtitle.isNotEmpty()) setSubtitle(subtitle)
                if (description.isNotEmpty()) setDescription(description)
            }
            .setNegativeButtonText(negativeButtonText)
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}