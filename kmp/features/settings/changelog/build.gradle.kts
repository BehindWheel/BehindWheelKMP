import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.settings.changelog"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.compose.commonUi)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.shared.crashlytics)
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.extensions)

            implementation(libs.decompose)
            implementation(libs.dev.gitlive.firebase.firestore)
            implementation(libs.koin.core)
            implementation(libs.kotlin.collections)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        commonTestDependencies {
            implementation(libs.kotlin.test)
        }
    }
}