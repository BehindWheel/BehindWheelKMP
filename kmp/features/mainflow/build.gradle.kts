import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
    alias(libs.plugins.grodnoroads.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.mainflow"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.tabs)
            implementation(projects.kmp.features.settings.alerts)
            implementation(projects.kmp.features.settings.appearance)
            implementation(projects.kmp.features.settings.changelog)
            implementation(projects.kmp.features.settings.faq)
            implementation(projects.kmp.features.settings.map)
            implementation(projects.kmp.shared.models)
            implementation(projects.kmp.libraries.coroutines)

            compileOnly(libs.compose.stable.marker)
            implementation(libs.decompose)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)

            implementation(libs.decompose.compose.jetpack)
        }
    }
}