import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.tabs")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.features.appSettings)
            implementation(projects.kmp.features.guidance)
            implementation(projects.kmp.features.eventReporting)
            implementation(projects.kmp.compose.foundation.icons)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.shared.models)
            implementation(projects.kmp.libraries.extensions)

            implementation(libs.compose.material3.windowsize)
            implementation(libs.decompose.compose)
            implementation(libs.decompose)
        }
    }
}
