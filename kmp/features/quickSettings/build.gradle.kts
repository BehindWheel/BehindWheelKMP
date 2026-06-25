import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.quicksettings")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.commonUi)
            implementation(projects.kmp.compose.foundation.icons)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.shared.persistent)

            implementation(libs.decompose)
            implementation(libs.koin.core)

            implementation(libs.bundles.mvikotlin)
        }
    }
}
