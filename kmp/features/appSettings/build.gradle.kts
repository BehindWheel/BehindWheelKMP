import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.appsettings")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.commonUi)
            implementation(projects.kmp.compose.foundation.icons)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.shared.components)
            implementation(projects.kmp.shared.models)

            implementation(libs.decompose)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.androidx.browser)
            implementation(libs.androidx.core)
        }
    }
}
