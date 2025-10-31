package com.jfpsolucoes.unipplus2.core.security

import android.util.Log
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
        onFailed: () -> Unit,
        onCancel: () -> Unit
    )

    fun cancel()
}

object UPBiometricManagerImpl : UPBiometricManager {
    private lateinit var biometricManager: BiometricManager

    private lateinit var biometricPrompt: BiometricPrompt
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
        onFailed: () -> Unit,
        onCancel: () -> Unit
    ) {
        biometricPrompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    when (errorCode) {
                        BiometricPrompt.ERROR_USER_CANCELED,
                        BiometricPrompt.ERROR_CANCELED,
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> onCancel()
                        else -> onError(errorCode, activity.getString(R.string.biometric_error_text))
                    }
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

    override fun cancel() {
        biometricPrompt.cancelAuthentication()
    }
}