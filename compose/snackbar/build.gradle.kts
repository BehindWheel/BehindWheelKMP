plugins {
    alias(libs.plugins.grodnoroads.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.compose.snackbar"
}

dependencies {
    implementation(projects.kmp.compose.foundation.uikit)
    implementation(projects.kmp.compose.resources)
    implementation(projects.kmp.libraries.extensions)
}