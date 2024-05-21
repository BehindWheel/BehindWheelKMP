import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
    alias(libs.plugins.grodnoroads.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.specialevent"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.logger)

            implementation(libs.decompose)
            implementation(libs.koin.core)
            implementation(libs.kotlin.coroutines)
            implementation(libs.kotlin.datetime)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)
            implementation(projects.compose.commonUi)
            implementation(projects.libraries.localization)
        }
        commonTestDependencies {
            implementation(libs.kotlin.test)
        }
    }
}