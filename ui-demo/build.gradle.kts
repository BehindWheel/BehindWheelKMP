import com.egoriku.grodnoroads.extension.debug
import com.egoriku.grodnoroads.extension.provideVersionCode
import com.egoriku.grodnoroads.extension.provideVersionName
import com.egoriku.grodnoroads.extension.release

plugins {
    id("grodnoroads.application")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.egoriku.grodnoroads.uidemo"

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads.uidemo"

        minSdk = libs.versions.minSdk.get().toInt()
        compileSdk = libs.versions.compileSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()

        versionCode = provideVersionCode("app/version.properties")
        versionName = provideVersionName("app/version.properties")
    }

    signingConfigs {
        debug {
            storeFile = keyStoreFile("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        release {
            storeFile = keyStoreFile("keystore.jks", "debug.keystore")
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
            isMinifyEnabled = true
            proguardFiles(
                "proguard-rules.pro",
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
            isShrinkResources = true
        }
    }
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)
    implementation(projects.compose.foundation.uikit)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
}

fun keyStoreFile(vararg fileNames: String): File? {
    for (path in fileNames) {
        val file = project.file(path)

        if (file.exists()) {
            return file
        }
    }

    return null
}