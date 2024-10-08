import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.mainflow"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.tabs)
            implementation(projects.kmp.features.settings.alerts)
            implementation(projects.kmp.features.settings.appearance)
            implementation(projects.kmp.features.settings.changelog)
            implementation(projects.kmp.features.settings.faq)
            implementation(projects.kmp.features.settings.map)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.shared.models)
            implementation(projects.kmp.libraries.coroutines)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)
        }
    }
}