import com.egoriku.grodnoroads.extension.debug
import com.egoriku.grodnoroads.extension.loadKeystore
import com.egoriku.grodnoroads.extension.provideVersionCode
import com.egoriku.grodnoroads.extension.provideVersionName
import com.egoriku.grodnoroads.extension.release

plugins {
    alias(libs.plugins.grodnoroads.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.egoriku.grodnoroads"

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads"

        minSdk = libs.versions.minSdk.get().toInt()
        compileSdk = libs.versions.compileSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()

        versionCode = provideVersionCode("$rootDir/config/versioning/android.properties")
        versionName = provideVersionName("$rootDir/config/versioning/android.properties")
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

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.kmp.features.root)
    implementation(projects.kmp.compose.foundation.core)
    implementation(projects.kmp.compose.foundation.theme)
    implementation(projects.kmp.compose.resources)
    implementation(projects.kmp.shared.analytics)
    implementation(projects.kmp.shared.crashlytics)
    implementation(projects.kmp.shared.persistent)
    implementation(projects.kmp.libraries.logger)

    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.google.maps)
    implementation(libs.google.material)
    implementation(libs.material3.windowsize)

    coreLibraryDesugaring(libs.desugar.jdk)
}

secrets {
    propertiesFileName = "secrets.properties"
}