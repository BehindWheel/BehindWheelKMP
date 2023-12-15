import com.egoriku.grodnoroads.extension.debug
import com.egoriku.grodnoroads.extension.loadKeystore
import com.egoriku.grodnoroads.extension.release

plugins {
    id("grodnoroads.application")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.kmp"

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads.kmp"

        minSdk = libs.versions.minSdk.get().toInt()
        compileSdk = libs.versions.compileSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()

        versionCode = 1
        versionName = "1.0.0"
        resourceConfigurations += listOf("en", "ru", "be-rBY")
    }

    signingConfigs {
        debug {
            storeFile = loadKeystore("$rootDir/config/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        release {
            storeFile = loadKeystore("$rootDir/config/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.theme)
    implementation(projects.kmp.features.root)
    implementation(projects.kmp.shared.components)
    implementation(projects.libraries.resources)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3.window)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.decompose)
    implementation(libs.google.material)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
}