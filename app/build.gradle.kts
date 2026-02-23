
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.jfpsolucoes.unipplus2"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.jfpsolucoes.unipplus2"
        minSdk = 24
        targetSdk = 36
        versionCode = 131
        versionName = "1.3.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("../apkkey/unipplus2_keystore.jks")
            storePassword = "Fr@544566"
            keyAlias = "unippluskey"
            keyPassword = "Fr@544566"
        }
    }

    buildTypes {
        val BASE_URL_FIELD = "BASE_URL"
        val API_AUTH_FIELD = "API_AUTH"
        val API_SECRETARY_FIELD = "API_SECRETARY"

        val REMOTE_BASE_URL = "\"https://southamerica-east1-unip-plus-2-a3fa1.cloudfunctions.net\""
        val REMOTE_API_AUTH = "\"api/auth\""
        val REMOTE_API_SECRETARY = "\"api/secretary\""

        val LOCAL_BASE_URL = "\"http://10.0.2.2:5001\""
        val LOCAL_API_AUTH = "\"/unip-plus-2-a3fa1/southamerica-east1/api/auth\""
        val LOCAL_API_SECRETARY = "\"/unip-plus-2-a3fa1/southamerica-east1/api/secretary\""

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk {
                debugSymbolLevel = "SYMBOL_TABLE"
            }

            buildConfigField("String", BASE_URL_FIELD, REMOTE_BASE_URL)
            buildConfigField("String", API_AUTH_FIELD, REMOTE_API_AUTH)
            buildConfigField("String", API_SECRETARY_FIELD, REMOTE_API_SECRETARY)
        }
        debug {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", BASE_URL_FIELD, REMOTE_BASE_URL)
            buildConfigField("String", API_AUTH_FIELD, REMOTE_API_AUTH)
            buildConfigField("String", API_SECRETARY_FIELD, REMOTE_API_SECRETARY)
        }
        create("debug_local") {
            initWith(getByName("debug"))
            isDebuggable = true

            buildConfigField("String", BASE_URL_FIELD, LOCAL_BASE_URL)
            buildConfigField("String", API_AUTH_FIELD, LOCAL_API_AUTH)
            buildConfigField("String", API_SECRETARY_FIELD, LOCAL_API_SECRETARY)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("debug_local") {
            assets {
                srcDirs("src/debug_local/assets")
            }
            java {
                srcDirs("src/debug_local/java")
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.adaptive.layout.suite)
    implementation(libs.androidx.adaptive)
    implementation(libs.androidx.adaptive.layout)
    implementation(libs.androidx.adaptive.navigation)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.window)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Biometric
    implementation(libs.androidx.biometric)

    // Room Database with encryption
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.sqlite.ktx)
    implementation(libs.sqlcipher)
    ksp(libs.androidx.room.compiler)

    // RETROFIT
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.scalars)

    // OKHTTP
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // GSON
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization)

    // BILLING
    implementation(libs.billing)

    implementation(libs.play.services.location)

    // FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.play.services.ads)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.remoteconfig)
    implementation(libs.firebase.database)
    implementation(libs.firebase.inappmessaging.display)

    // Play Store
    implementation(libs.playstore.app.update.ktx)
    implementation(libs.playstore.app.review.ktx)

    // TESTING
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.tooling.preview)
}