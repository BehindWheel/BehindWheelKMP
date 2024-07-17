import com.egoriku.grodnoroads.extension.debug
import com.egoriku.grodnoroads.extension.loadKeystore
import com.egoriku.grodnoroads.extension.release

plugins {
    alias(libs.plugins.grodnoroads.application)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.uidemo"

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads.uidemo"

        minSdk = libs.versions.minSdk.get().toInt()
        compileSdk = libs.versions.compileSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()

        versionCode = 1
        versionName = "1.0.0"
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
}

dependencies {
    implementation(projects.kmp.compose.foundation.icons)
    implementation(projects.kmp.compose.foundation.uikit)
    implementation(projects.kmp.compose.resources)
    implementation(projects.kmp.compose.snackbar)
    implementation(projects.kmp.libraries.extensions)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)

    implementation(compose.preview)
    debugImplementation(compose.uiTooling)
}