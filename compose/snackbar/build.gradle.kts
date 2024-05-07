plugins {
    alias(libs.plugins.grodnoroads.library)
    alias(libs.plugins.grodnoroads.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.compose.snackbar"
}

dependencies {
    implementation(projects.compose.foundation.uikit)
    implementation(projects.kmp.shared.resources)
    implementation(projects.kmp.libraries.extensions)
}