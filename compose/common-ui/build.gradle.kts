plugins {
    alias(libs.plugins.grodnoroads.library)
    alias(libs.plugins.grodnoroads.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.common.ui"
}

dependencies {
    implementation(projects.compose.foundation.uikit)
    implementation(projects.kmp.shared.resources)
    implementation(projects.libraries.localization)

    implementation(libs.kotlin.collections)
}