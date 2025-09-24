
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    id("kotlin-parcelize")
}

android {
    namespace = "com.jfpsolucoes.unipplus2"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.jfpsolucoes.unipplus2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://southamerica-east1-unip-plus-2-a3fa1.cloudfunctions.net\"")
            buildConfigField("String", "API_AUTH", "\"/auth\"")
            buildConfigField("String", "API_SECRETARY", "\"/secretary\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:5001\"")
            buildConfigField("String", "API_AUTH", "\"/unip-plus-2-a3fa1/southamerica-east1/auth\"")
            buildConfigField("String", "API_SECRETARY", "\"/unip-plus-2-a3fa1/southamerica-east1/secretary\"")
        }
        create("local") {
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:5001\"")
            buildConfigField("String", "API_AUTH", "\"/unip-plus-2-a3fa1/southamerica-east1/auth\"")
            buildConfigField("String", "API_SECRETARY", "\"/unip-plus-2-a3fa1/southamerica-east1/secretary\"")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    // FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.play.services.ads)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // TESTING
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // SVG Parser
    implementation(libs.android.svg)
}