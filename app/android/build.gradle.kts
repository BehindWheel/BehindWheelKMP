import com.egoriku.grodnoroads.extension.*

plugins {
    id("grodnoroads.application")
    id("grodnoroads.compose")
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.egoriku.grodnoroads"

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads"

        minSdk = libs.versions.minSdk.get().toInt()
        compileSdk = libs.versions.compileSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()

        versionCode = provideVersionCode("$projectDir/version.properties")
        versionName = provideVersionName("$projectDir/version.properties")
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
            storeFile = loadKeystore("keystore.jks", "$rootDir/config/debug.keystore")
            storePassword = System.getenv("KEY_STORE_PASSWORD") ?: "android"
            keyAlias = System.getenv("KEY_ALIAS") ?: "androiddebugkey"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "android"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }

        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.features.map.mapData)
    implementation(projects.features.map.mapDomain)
    implementation(projects.features.map.mapUi)

    implementation(projects.features.settings)

    implementation(projects.features.setting.alerts)
    implementation(projects.features.setting.appearance)
    implementation(projects.features.setting.changelog)
    implementation(projects.features.setting.faq)
    implementation(projects.features.setting.map)

    implementation(projects.shared.appSettings)
    implementation(projects.shared.appComponent)

    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)
    implementation(projects.compose.foundation.uikit)

    implementation(projects.compose.commonUi)

    implementation(projects.libraries.analytics)
    implementation(projects.libraries.crashlytics)
    implementation(projects.libraries.extensions)
    implementation(projects.libraries.location)
    implementation(projects.libraries.resources)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.window)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.coroutines)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.decompose)
    implementation(libs.decompose.compose.jetpack)
    implementation(libs.immutable.collections)
    implementation(libs.google.maps)
    implementation(libs.google.material)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin)
}

secrets {
    propertiesFileName = "secrets.properties"
}