import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.settings.debugtools")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.commonUi)
            implementation(projects.kmp.compose.foundation.icons)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.foundation.navigation)
            implementation(projects.kmp.compose.snackbar)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.shared.auth)
            implementation(projects.kmp.shared.persistent)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.features.specialEventReminder)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)
            implementation(libs.essenty.lifecycle.coroutines)
            implementation(libs.koin.core)
        }
    }
}
