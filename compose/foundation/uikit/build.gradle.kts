plugins {
    alias(libs.plugins.grodnoroads.library)
    alias(libs.plugins.grodnoroads.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.uikit"
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)

    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.material3)
}