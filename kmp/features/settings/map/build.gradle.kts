import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.settings.map"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.compose.commonUi)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.shared.persistent)
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.location)

            implementation(libs.decompose)
            implementation(libs.koin.core)
            implementation(libs.kotlin.collections)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.kmp.shared.resources)
            implementation(projects.libraries.localization)

            implementation(libs.coil)
            implementation(compose.components.resources)
        }
    }
}