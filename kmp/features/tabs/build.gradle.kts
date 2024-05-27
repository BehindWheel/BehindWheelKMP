import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.tabs"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.appSettings)
            implementation(projects.kmp.features.guidance)
            implementation(projects.kmp.features.eventReporting)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.shared.models)
            implementation(projects.kmp.libraries.coroutines)

            implementation(libs.decompose)
        }
        androidDependencies {
            implementation(libs.material3.windowsize)
            implementation(libs.decompose.compose)
            implementation(libs.kotlin.collections)
        }
    }
}