plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "org.fcitx.fcitx5.android.plugin.clipboard_sync"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.fcitx.fcitx5.android.plugin.clipboard_sync"
        minSdk = 24
        targetSdk = 35

        versionCode = 1
        versionName = "0.1.2"

        val mainApplicationId = (project.findProperty("mainApplicationId") as? String)
            ?: System.getenv("MAIN_APPLICATION_ID")
            ?: "org.fcitx.fcitx5.android"

        manifestPlaceholders["mainApplicationId"] = mainApplicationId
        buildConfigField("String", "MAIN_APPLICATION_ID", "\"$mainApplicationId\"")
    }

    buildFeatures {
        aidl = true
        buildConfig = true
    }

    val signingKeystorePath = System.getenv("SIGNING_KEYSTORE_FILE")
        ?: System.getenv("SIGNING_KEYSTORE_PATH")
    val signingKeyAlias = System.getenv("SIGNING_KEY_ALIAS")
    val signingKeyPassword = System.getenv("SIGNING_KEY_PASSWORD")
    val signingStorePassword = System.getenv("SIGNING_STORE_PASSWORD")

    val releaseSigningConfig =
        if (
            !signingKeystorePath.isNullOrBlank() &&
            !signingKeyAlias.isNullOrBlank() &&
            !signingKeyPassword.isNullOrBlank() &&
            !signingStorePassword.isNullOrBlank()
        ) {
            signingConfigs.create("release") {
                storeFile = file(signingKeystorePath)
                storePassword = signingStorePassword
                keyAlias = signingKeyAlias
                keyPassword = signingKeyPassword
            }
        } else {
            null
        }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            if (releaseSigningConfig != null) {
                signingConfig = releaseSigningConfig
            }
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("androidx.documentfile:documentfile:1.0.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}

